package ru.otus.exceptions;

public class DoesntHaveRequiredBanknotesException extends RuntimeException{

    public DoesntHaveRequiredBanknotesException(String message) {
        super(message);
    }
}
