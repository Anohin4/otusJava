package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    EntityClassMetaData<T> metaData;
    private String SELECT_ALL_SQL;
    private String SELECT_BY_ID_SQL;
    private String INSERT_SQL;
    private String UPDATE_SQL;


    public EntitySQLMetaDataImpl(EntityClassMetaData<T> metaData) {
        this.metaData = metaData;
        this.SELECT_ALL_SQL = initSelectAllSql();
        this.SELECT_BY_ID_SQL = initSelectById();
        this.INSERT_SQL = initInsertSql();
        this.UPDATE_SQL = initUpdateSql();
    }

    @Override
    public String getSelectAllSql() {

        return SELECT_ALL_SQL;
    }

    @Override
    public String getSelectByIdSql() {
        return SELECT_BY_ID_SQL;
    }

    @Override
    public String getInsertSql() {
        return INSERT_SQL;
    }

    @Override
    public String getUpdateSql() {
        return UPDATE_SQL;
    }

    private String initSelectAllSql() {
        String name = metaData.getName();
        return "SELECT * FROM " + name;
    }

    private String initSelectById() {
        String name = metaData.getName();
        String idField = metaData.getIdField().getName();
        return "SELECT * FROM " + name + " WHERE " + idField + "=?";
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
}
