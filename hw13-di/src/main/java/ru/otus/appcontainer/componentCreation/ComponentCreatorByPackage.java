package ru.otus.appcontainer.componentCreation;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class ComponentCreatorByPackage extends AbstractComponentCreator {
    private final String packageName;

    public ComponentCreatorByPackage(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void registerComponentsToContainer(AppComponentsContainer diContainer) {
        processConfigurationByPackageName(packageName, diContainer);
    }

    private void processConfigurationByPackageName(String packageName, AppComponentsContainer diContainer) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated =
                reflections.get(SubTypes.of(TypesAnnotated.with(AppComponentsContainerConfig.class)).asClass());
        List<Class<?>> sortedContainers = getContainersInCorrectOrder(annotated);
        for (Class<?> appComponent : sortedContainers) {
            processConfigClass(appComponent,diContainer);
        }

    }
}
