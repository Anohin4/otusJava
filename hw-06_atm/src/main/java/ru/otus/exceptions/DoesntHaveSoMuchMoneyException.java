package ru.otus.exceptions;

public class DoesntHaveSoMuchMoneyException extends RuntimeException{

    public DoesntHaveSoMuchMoneyException(String message) {
        super(message);
    }
}
