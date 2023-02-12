package proxy;

import static proxy.ReflectionHelper.toClasses;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

        public MyInvocationHandler(MyTestClass testClass) {

            this.testClass = testClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method methodFromOurClass = testClass
                    .getClass()
                    .getMethod(method.getName(), toClasses(args));

            if (methodFromOurClass.isAnnotationPresent(Log.class)) {
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
