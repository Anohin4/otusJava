package ru.otus.exceptions.handling;

public class TearDownExceptionHandler implements ExceptionHandler {

    @Override
    public void handleException(Exception exception) throws Exception {
        System.out.println("Ошибка во время выполнения блока after, останавливаем выполнение тестов");
        throw new RuntimeException("Выполнение тестов остановлено");
    }
}
