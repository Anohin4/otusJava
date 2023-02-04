package ru.otus.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleTestStep implements TestStep {

    private final Object objectToTest;
    private final Method method;

    public SingleTestStep(Method method, Object objectToTest) {
        this.objectToTest = objectToTest;
        this.method = method;
    }

    @Override
    public void execute() throws Throwable {
        try {
            method.invoke(objectToTest);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
