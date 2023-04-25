package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> metaData;
    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;


    public EntitySQLMetaDataImpl(EntityClassMetaData<?> metaData) {
        this.metaData = metaData;
        this.selectAllSql = initSelectAllSql();
        this.selectByIdSql = initSelectById();
        this.insertSql = initInsertSql();
        this.updateSql = initUpdateSql();
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }

    private String initSelectAllSql() {
        String name = metaData.getName();
        String fieldNames = getFiledNames();
        return "SELECT "+ fieldNames +" FROM " + name;
    }

    private String initSelectById() {
        String name = metaData.getName();
        String idField = metaData.getIdField().getName();
        String fieldNames = getFiledNames();
        return "SELECT "+ fieldNames +" FROM " + name + " WHERE " + idField + "=?";
    }

    private String initInsertSql() {
        List<String> fieldNames = metaData.getFieldsWithoutId()
                .stream()
                .map(Field::getName)
                .toList();
        String join = String.join(", ", fieldNames);

        String values = "(" + "?,".repeat(fieldNames.size());
        values = values.substring(0, values.lastIndexOf(",")) + ")";

        String name = metaData.getName();
        return "INSERT INTO " + name + " (" + join + ") VALUES " + values;
    }

    private String initUpdateSql() {
        String tableName = metaData.getName();
        String idFieldName = metaData.getIdField().getName();
        String setString = metaData.getFieldsWithoutId()
                .stream()
                .map(Field::getName)
                .map(it -> it + "=?, ")
                .collect(Collectors.joining());
        setString = setString.substring(0, setString.lastIndexOf(","));
        return "UPDATE " + tableName + " SET " + setString + " where " + idFieldName + "=?";
    }

    private String getFiledNames() {
        List<String> fieldNames = metaData.getAllFields()
                .stream()
                .map(Field::getName)
                .toList();
        return String.join(", ", fieldNames);
    }
}
