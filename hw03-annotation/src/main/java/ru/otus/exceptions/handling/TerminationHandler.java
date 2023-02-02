package ru.otus.exceptions.handling;

import java.util.Deque;
import ru.otus.command.TestStep;

public class TerminationHandler implements ExceptionHandler {

    private final Deque<TestStep> testStepQueue;

    public TerminationHandler(Deque<TestStep> testStepQueue) {
        this.testStepQueue = testStepQueue;
    }

    @Override
    public void handleException(Exception exception) throws Exception {
        testStepQueue.clear();
        System.out.println("Останавливаем прохождение тестов, ");
    }
}
