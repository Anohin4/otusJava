package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.SneakyThrows;

public class EntitySQLParamMetaDataImpl<T> implements EntitySQLParamsMetaData<T> {
    private final EntityClassMetaData<T> metaData;

    public EntitySQLParamMetaDataImpl(EntityClassMetaData<T> metaData) {
        this.metaData = metaData;
    }

    @Override
    @SneakyThrows
    public List<Object> getSelectByIdParams(Object id) {
        return Collections.singletonList(id);
    }

    @Override
    @SneakyThrows
    public List<Object> getInsertParams(T object) {
        List<Object> result = new ArrayList<>();
        for (Field f : metaData.getFieldsWithoutId()) {
            f.setAccessible(true);
            result.add(f.get(object));
        }
        return result;
    }

    @Override
    @SneakyThrows
    public List<Object> getUpdateParams(T object) {
        List<Object> result = new ArrayList<>();
        for (Field field : metaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            result.add(field.get(object));
        }
        Field idField = metaData.getIdField();
        result.add(idField.get(object));
        return result;
    }
}
