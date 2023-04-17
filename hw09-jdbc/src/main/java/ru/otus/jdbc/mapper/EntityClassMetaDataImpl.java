package ru.otus.jdbc.mapper;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import ru.otus.annotations.Id;
import ru.otus.jdbc.mapper.exceptions.NoAllArgumentsConstructorException;
import ru.otus.jdbc.mapper.exceptions.NoIdFieldException;
import ru.otus.jdbc.mapper.exceptions.SeveralIdFieldsException;

@SuppressWarnings("unchecked")
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private Class<T> persistentClass;
    private String name;
    private Field idField;
    private final List<Field> notIdFields;
    private Constructor<T> tConstructor;

    public EntityClassMetaDataImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        notIdFields = new ArrayList<>();
        initMetaData();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return tConstructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>(notIdFields);
        fields.add(idField);
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return new ArrayList<>(notIdFields);
    }

    private void initMetaData() {
        initFields();
        initConstructor();
        this.name = persistentClass.getSimpleName();
    }

    private void initFields() {
        for (Field field : persistentClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                if (nonNull(idField)) {
                    throw new SeveralIdFieldsException("There are several fields with Id annotation");
                }
                this.idField = field;
            } else {
                notIdFields.add(field);
            }
        }
        if (isNull(idField)) {
            throw new NoIdFieldException("Can't find Id in class");
        }
    }

    private void initConstructor() {
        for (Constructor<?> constructor : persistentClass.getConstructors()) {
            //полный конструктор это количество не ид полей + ид поле
            if (constructor.getParameterCount() == notIdFields.size() + 1) {
                this.tConstructor = (Constructor<T>) constructor;
                return;
            }
        }
        throw new NoAllArgumentsConstructorException("Can't find constructor for all arguments");
    }

}
