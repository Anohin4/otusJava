package ru.otus.jdbc.mapper.exceptions;

public class SeveralIdFieldsException extends RuntimeException {

    public SeveralIdFieldsException(String message) {
        super(message);
    }
}
