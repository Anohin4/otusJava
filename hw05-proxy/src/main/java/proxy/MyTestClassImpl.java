package proxy;

import proxy.annotation.Log;

public class MyTestClassImpl implements MyTestClass {

    @Override
    @Log
    public String doSmth(String argOne) {
        System.out.println("this is methodOne");
        return "123";

    }

    @Override
    @Log
    public void doSmth(String argOne, Integer argTwo) {

    }

    @Override
    @Log
    public void doSmth(String argOne, Double argTwo, Boolean bool) {

    }
}
