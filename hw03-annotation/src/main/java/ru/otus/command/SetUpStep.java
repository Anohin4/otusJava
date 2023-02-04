package ru.otus.command;

import java.lang.reflect.Method;
import java.util.List;
import ru.otus.exceptions.SetUpException;

public class SetUpStep implements TestStep {


    private final List<Method> steps;
    private final Object testObject;

    public SetUpStep(List<Method> steps, Object testObject) {
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
            throw new SetUpException(
                    "Ошибка во время шага \"Before\". Изначальная ошибка: " + e.getCause().getMessage());
        }
    }
}
