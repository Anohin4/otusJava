package ru.otus.dbLayer.core.sessionmanager;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

    <T> T doInReadOnlyTransaction(TransactionAction<T> action);
}
