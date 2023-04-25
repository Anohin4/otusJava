package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import ru.otus.core.repository.DataTemplateException;

public class EntityResultSetFunctions<T> {

    private final EntityClassMetaData<T> metadata;

    public EntityResultSetFunctions(EntityClassMetaData<T> entityClassMetaDataClient) {
        this.metadata = entityClassMetaDataClient;
    }

    public Function<ResultSet, List<T>> getRsListFunction() {
        Function<ResultSet, T> rsFunctionForOneRow = getRsFunction();
        return resultSet -> {
            List<T> objects = new ArrayList<>();
            Optional<T> objectToPut;
            do {
                objectToPut = Optional.ofNullable(rsFunctionForOneRow.apply(resultSet));
                objectToPut.ifPresent(objects::add);
            } while (objectToPut.isPresent());

            return objects;
        };
    }

    public Function<ResultSet, T> getRsFunction() {
        return resultSet -> {
            //результат пустой - возвращаем нулл
            try {
                if (!resultSet.next()) {
                    return null;
                }
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }

            // иначе мапим поля и возвращаем
            T resultObject = getEmptyResultObject();
            setFieldsValuesOfObject(resultSet, resultObject);
            return resultObject;
        };
    }

    private T getEmptyResultObject() {
        Constructor<T> constructor = metadata.getConstructor();
        try {
            return constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldsValuesOfObject(ResultSet resultSet, T resultObject) {
        List<Field> allFields = metadata.getAllFields();
        for (Field field : allFields) {
            try {
                field.setAccessible(true);
                field.set(resultObject, getObjectToPut(resultSet, field));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Object getObjectToPut(ResultSet resultSet, Field field) throws SQLException {
        String name = field.getName();
        return resultSet.getObject(name);

    }
}
