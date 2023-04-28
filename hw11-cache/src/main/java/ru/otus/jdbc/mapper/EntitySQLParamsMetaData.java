package ru.otus.jdbc.mapper;

import java.util.List;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLParamsMetaData<T> {

    List<Object> getSelectByIdParams(Object id);

    List<Object> getInsertParams(T object);

    List<Object> getUpdateParams(T object);
}
