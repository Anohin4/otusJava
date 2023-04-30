package ru.otus.appcontainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.componentCreation.ComponentCreatorByClass;
import ru.otus.appcontainer.componentCreation.ComponentCreatorByPackage;
import ru.otus.appcontainer.exceptions.AmbiguousComponentException;
import ru.otus.appcontainer.exceptions.DuplicateComponentException;
import ru.otus.appcontainer.exceptions.NoSuchComponentException;

@SuppressWarnings("unchecked")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();


    public AppComponentsContainerImpl(Class<?>... classes) {
        var componentCreator = new ComponentCreatorByClass(classes);
        componentCreator.registerComponentsToContainer(this);

    }

    public AppComponentsContainerImpl(String packageName) {
        var componentCreator = new ComponentCreatorByPackage(packageName);
        componentCreator.registerComponentsToContainer(this);

    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<C> first = appComponents.stream().filter(it -> componentClass.isAssignableFrom(it.getClass()))
                .map(it -> (C) it)
                .toList();
        if (first.isEmpty()) {
            throw new NoSuchComponentException("Can't find component for class " + componentClass.getName());
        }
        if (first.size() > 1) {
            throw new AmbiguousComponentException(
                    "There are more than one component for class " + componentClass.getName());
        }
        return first.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (!appComponentsByName.containsKey(componentName)) {
            throw new NoSuchComponentException("Can't find component for name " + componentName);
        }
        return (C) appComponentsByName.get(componentName);
    }

    @Override
    public <C> void addComponent(String name, C component) {
        if (appComponentsByName.containsKey(name)) {
            throw new DuplicateComponentException("We already have a component with name " + name);
        }

        appComponents.add(component);
        appComponentsByName.put(name, component);
    }
}
