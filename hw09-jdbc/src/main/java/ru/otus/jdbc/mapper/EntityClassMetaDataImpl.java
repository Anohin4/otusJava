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

    private final Class<T> persistentClass;
    private final String name;
    private final Field idField;
    private final List<Field> notIdFields;
    private final List<Field> allFields;
    private final Constructor<T> tConstructor;

    public EntityClassMetaDataImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        this.notIdFields = new ArrayList<>();
        this.allFields = new ArrayList<>();
        this.idField = initIdField();
        this.name = persistentClass.getSimpleName();
        initFieldsListsValues();
        this.tConstructor = initConstructor();
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
        return new ArrayList<>(allFields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return new ArrayList<>(notIdFields);
    }

    private void initFieldsListsValues() {
        for (Field field : persistentClass.getDeclaredFields()) {
            if (!field.equals(idField)) {
                notIdFields.add(field);
            }
            allFields.add(field);
        }
    }

    private Field initIdField() {
        Field id = null;
        for (Field field : persistentClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                if (nonNull(idField)) {
                    throw new SeveralIdFieldsException("There are several fields with Id annotation");
                }
                id = field;
            }
        }
        if (isNull(id)) {
            throw new NoIdFieldException("Can't find Id in class");
        }
        return id;
    }

    private Constructor<T> initConstructor() {
        for (Constructor<?> constructor : persistentClass.getConstructors()) {
            //полный конструктор это количество не ид полей + ид поле
            if (constructor.getParameterCount() == notIdFields.size() + 1) {
                return (Constructor<T>) constructor;
            }
        }
        throw new NoAllArgumentsConstructorException("Can't find constructor for all arguments");
    }

}
