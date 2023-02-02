package ru.otus.exceptions.handling;

import java.util.Deque;
import ru.otus.command.TestStep;

public class SetUpExceptionHandler implements ExceptionHandler {

    private final Deque<TestStep> testStepQueue;

    public SetUpExceptionHandler(Deque<TestStep> testStepQueue) {
        this.testStepQueue = testStepQueue;
    }

    @Override
    public void handleException(Exception exception) throws Exception {
        System.out.println(exception.getMessage());
        System.out.println("Выполняем действия after и прекращаем выполнение тестов");

        TestStep stepAfter = testStepQueue.getLast();
        testStepQueue.clear();
        stepAfter.execute();

        throw new RuntimeException("Выполнение тестов остановлено");
    }

}
