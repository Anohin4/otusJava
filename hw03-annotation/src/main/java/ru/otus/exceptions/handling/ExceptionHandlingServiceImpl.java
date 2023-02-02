package ru.otus.exceptions.handling;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import ru.otus.command.TestStep;
import ru.otus.exceptions.SetUpException;

public class ExceptionHandlingServiceImpl implements ExceptionHandlingService {

    private Map<Class<? extends Exception>, ExceptionHandler> handlerRepo;
    private final Deque<TestStep> testStepQueue;

    public ExceptionHandlingServiceImpl(Deque<TestStep> testStepQueue) {
        this.testStepQueue = testStepQueue;
        this.handlerRepo = new HashMap<>();
        handlerRepo.put(SetUpException.class, new SetUpExceptionHandler(testStepQueue));
    }

    @Override
    public void handleException(Exception e) throws Exception {
        if (handlerRepo.containsKey(e.getClass())) {
            handlerRepo.get(e.getClass()).handleException(e);
            return;
        }
        //если дошли сюда - что-то пошло не так, ошибка которую мы не знаем как обработать
        new TerminationHandler(testStepQueue);
    }
}
