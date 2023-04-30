package ru.otus.appcontainer.componentCreation;

import ru.otus.appcontainer.api.AppComponentsContainer;

public interface ComponentCreator {
    void registerComponentsToContainer(AppComponentsContainer diContainer);
}
