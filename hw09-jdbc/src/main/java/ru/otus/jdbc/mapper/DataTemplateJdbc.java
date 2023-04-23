package ru.otus.jdbc.mapper;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    protected final EntitySQLMetaData entitySQLMetaData;
    protected final EntityResultSetFunctions<T> functions;
    protected final EntitySQLParamsMetaData<T> sqlParams;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntityClassMetaData<T> entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = new EntitySQLMetaDataImpl(entitySQLMetaData);
        this.functions = new EntityResultSetFunctions<>(entitySQLMetaData);
        this.sqlParams = new EntitySQLParamMetaDataImpl<>(entitySQLMetaData);
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.findOne(connection,
                entitySQLMetaData.getSelectByIdSql(),
                sqlParams.getSelectByIdParams(id),
                functions.getRsFunction());
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.findAll(connection,
                entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                functions.getRsListFunction());
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(connection,
                entitySQLMetaData.getInsertSql(),
                sqlParams.getInsertParams(client));
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(connection,
                entitySQLMetaData.getUpdateSql(),
                sqlParams.getUpdateParams(client));

    }
}
