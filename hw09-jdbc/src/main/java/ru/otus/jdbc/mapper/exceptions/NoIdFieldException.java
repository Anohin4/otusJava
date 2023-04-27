package ru.otus.jdbc.mapper.exceptions;

public class NoIdFieldException extends RuntimeException {

    public NoIdFieldException(String message) {
        super(message);
    }
}
