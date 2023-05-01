package ru.otus.appcontainer.componentCreation;

import static java.util.Objects.isNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.exceptions.AmbiguousConstructorException;
import ru.otus.appcontainer.exceptions.NoSuchComponentException;

public abstract class AbstractComponentCreator implements ComponentCreator {

    protected void processConfigClass(Class<?> appConfigClass, AppComponentsContainer diContainer) {
        Method[] methods = appConfigClass.getMethods();
        List<Method> sortedMethods = getSortedComponentCreationMethods(methods);
        for (Method sortedMethod : sortedMethods) {
            processConfigMethod(appConfigClass, sortedMethod, diContainer);
        }
    }

    protected void processConfigMethod(Class<?> appConfigClass, Method sortedMethod, AppComponentsContainer diContainer) {
        String name = sortedMethod.getAnnotation(AppComponent.class).name();
        if (isNull(name)) {
            name = appConfigClass.getSimpleName();
        }
        try {
            Object configObject = getConfigObject(appConfigClass, diContainer);
            Object componentFromMethod = getComponentFromMethod(sortedMethod, configObject,diContainer);
            diContainer.addComponent(name, componentFromMethod);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object getConfigObject(Class<?> appComponent, AppComponentsContainer diContainer)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = appComponent.getConstructors();
        if (constructors.length > 1) {
            throw new AmbiguousConstructorException(
                    "Configure class " + appComponent.getName() + " must have only one constructor");
        }
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length == 0) {
            return constructor.newInstance();
        }
        Object[] constructorParams = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            constructorParams[i] = diContainer.getAppComponent(parameterTypes[i]);
        }
        return constructor.newInstance(constructorParams);

    }

    protected Object getComponentFromMethod(Method method, Object configObject, AppComponentsContainer diContainer) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Parameter[] parameters = method.getParameters();
        Object[] methodParamsArray = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            String name = parameters[i].getName();
            //Если не находим компонент по имени - ищем по классу
            try {
                methodParamsArray[i] = diContainer.getAppComponent(name);
            } catch (NoSuchComponentException e) {
                methodParamsArray[i] = diContainer.getAppComponent(parameterTypes[i]);
            }
        }

        try {
            return method.invoke(configObject, methodParamsArray);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected List<Method> getSortedComponentCreationMethods(Method[] methods) {
        List<Method> methodList = Arrays.asList(methods);
        return methodList
                .stream()
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted((one, two) -> {
                    int orderOne = one.getAnnotation(AppComponent.class).order();
                    int orderTwo = two.getAnnotation(AppComponent.class).order();
                    return orderOne - orderTwo;
                })
                .toList();
    }

    protected List<Class<?>> getContainersInCorrectOrder(Set<Class<?>> annotated) {
        return annotated
                .stream()
                .sorted((one, two) -> {
                    int orderOne = one.getAnnotation(AppComponentsContainerConfig.class).order();
                    int orderTwo = two.getAnnotation(AppComponentsContainerConfig.class).order();
                    return orderOne - orderTwo;
                })
                .toList();
    }

}
