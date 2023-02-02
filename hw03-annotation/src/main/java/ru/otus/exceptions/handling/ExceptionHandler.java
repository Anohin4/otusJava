package ru.otus.exceptions.handling;

public interface ExceptionHandler {

    void handleException(Exception exception) throws Exception;
}
