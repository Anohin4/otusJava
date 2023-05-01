package ru.otus.appcontainer.exceptions;

public class AmbiguousComponentException extends RuntimeException{

    public AmbiguousComponentException(String message) {
        super(message);
    }
}
