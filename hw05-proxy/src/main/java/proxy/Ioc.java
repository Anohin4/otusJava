package proxy;

import static proxy.ReflectionHelper.getMethodNameWithParamsTypes;
import static proxy.ReflectionHelper.toClasses;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import proxy.annotation.Log;

class Ioc {

    private Ioc() {
    }

    public static MyTestClass createMytestClass() {
        MyTestClass myTestClass = new MyTestClassImpl();
        InvocationHandler testClassProxyHandler = new MyInvocationHandler(myTestClass);
        return (MyTestClass) Proxy.newProxyInstance(Ioc.class.getClassLoader(), myTestClass.getClass().getInterfaces(),
                testClassProxyHandler);

    }

    static class MyInvocationHandler implements InvocationHandler {

        private final MyTestClass testClass;
        private Map<String, Method> logMethodMap;

        public MyInvocationHandler(MyTestClass testClass) {
            this.testClass = testClass;
            logMethodMap = initMap();
        }

        private Map<String, Method> initMap() {
            Map<String, Method> result = new HashMap<>();
            for (Method method : testClass.getClass().getMethods()) {
                //если нет логирования - нам не интересно
                if (!method.isAnnotationPresent(Log.class)) {
                    continue;
                }
                String newName = getMethodNameWithParamsTypes(method, method.getParameterTypes());
                result.put(newName, method);
            }
            return result;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (logMethodMap.containsKey(getMethodNameWithParamsTypes(method, toClasses(args)))) {
                StringBuilder sb = new StringBuilder();
                for (Object arg : args) {
                    sb.append(arg.toString());
                    sb.append(", ");
                }

                System.out.println("Executed method " + method.getName() + " with args: " + sb);
            }
            return method.invoke(testClass, args);
        }


    }
}
