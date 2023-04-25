package ru.otus.atm.exceptions;

public class DoesntHaveRequiredBanknotesException extends RuntimeException {

    public DoesntHaveRequiredBanknotesException(String message) {
        super(message);
    }
}
