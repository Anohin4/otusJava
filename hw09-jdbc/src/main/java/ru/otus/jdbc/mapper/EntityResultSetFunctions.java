package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.SneakyThrows;

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


    @SneakyThrows
    public Function<ResultSet, T> getRsFunction() {
        return resultSet -> {
            //результат пустой - возвращаем нулл
            try {
                if (!resultSet.next()) {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // иначе мапим поля в конструктор и возвращаем
            List<Field> allFields = metadata.getAllFields();
            Object[] constructorArgs = new Object[allFields.size()];
            for (int i = 0; i < allFields.size(); i++) {
                Field field = allFields.get(i);
                constructorArgs[i] = getObjectToPut(resultSet, field);
            }
            Constructor<T> constructor = metadata.getConstructor();

            try {
                return constructor.newInstance(constructorArgs);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @SneakyThrows
    private Object getObjectToPut(ResultSet resultSet, Field field) {
        String name = field.getName();
        Class<?> type = field.getType();
        if (type == String.class) {
            return resultSet.getString(name);
        }
        if (type == Short.class) {
            return resultSet.getShort(name);
        }
        if (type == Boolean.class || type == boolean.class) {
            return resultSet.getBoolean(name);
        }
        if (type == Double.class) {
            return resultSet.getDouble(name);
        }
        if (type == Integer.class || type == int.class) {
            return resultSet.getInt(name);
        }
        if (type == Long.class || type == long.class) {
            return resultSet.getLong(name);
        }
        if (type == Float.class) {
            return resultSet.getLong(name);
        }
        throw new RuntimeException("qwe");

    }
}
