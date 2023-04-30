package ru.otus.appcontainer.exceptions;

public class AmbiguousConstructorException extends RuntimeException{

    public AmbiguousConstructorException(String message) {
        super(message);
    }
}
