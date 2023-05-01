package ru.otus.appcontainer.componentCreation;

import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class ComponentCreatorByClass extends AbstractComponentCreator {

    private final Class<?>[] classes;

    public ComponentCreatorByClass(Class<?>... classes) {
        this.classes = classes;

    }

    @Override
    public void registerComponentsToContainer(AppComponentsContainer diContainer) {
        for (Class<?> initialConfigClass : classes) {
            checkConfigClass(initialConfigClass);
            processConfigClass(initialConfigClass, diContainer);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

}
