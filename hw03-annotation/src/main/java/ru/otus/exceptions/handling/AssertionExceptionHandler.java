package ru.otus.exceptions.handling;

public class AssertionExceptionHandler implements ExceptionHandler{

    @Override
    public void handleException(Exception exception) throws Exception {
        System.out.println("Провал теста");
    }
}
