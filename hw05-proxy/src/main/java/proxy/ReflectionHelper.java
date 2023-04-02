package proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;


public class ReflectionHelper {
    private ReflectionHelper() {
    }


    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
    public static String getMethodNameWithParamsTypes(Method method, Class<?>[] argsArray) {
        String pamasTypeAsString = Arrays.toString(toClassesNamesString(argsArray));
        return method.getName() + pamasTypeAsString;
    }
    public static String[] toClassesNamesString(Class<?>[] args) {
        return Arrays.stream(args).map(Class::getName).toArray(String[]::new);
    }

}
