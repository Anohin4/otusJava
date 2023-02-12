package proxy;

import java.util.Arrays;


public class ReflectionHelper {
    private ReflectionHelper() {
    }


    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
}
