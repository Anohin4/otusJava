package ru.otus.command;

import java.lang.reflect.Method;
import java.util.List;
import ru.otus.exceptions.TearDownException;

public class TearDownTestStep implements TestStep {

    private List<Method> steps;
    private Object testObject;

    public TearDownTestStep(List<Method> steps, Object testObject) {
        this.steps = steps;
        this.testObject = testObject;
    }

    @Override
    public void execute() throws Exception {
        try {
            for (var testStep : steps) {
                testStep.invoke(testObject);
            }
        } catch (Exception e) {
            throw new TearDownException(e.getCause().getMessage());
        }
    }
}
