package ru.otus.jdbc.mapper.exceptions;

public class NoAllArgumentsConstructorException extends RuntimeException{

    public NoAllArgumentsConstructorException(String message) {
        super(message);
    }
}
