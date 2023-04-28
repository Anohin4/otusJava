package ru.otus.config;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbLayer.core.repository.DataTemplateHibernate;
import ru.otus.dbLayer.core.repository.HibernateUtils;
import ru.otus.dbLayer.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.dbLayer.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.model.db.Address;
import ru.otus.model.db.Client;
import ru.otus.model.db.Phone;
import ru.otus.dbLayer.crm.service.DbServiceClientImpl;
import ru.otus.mapping.ClientMapper;
import ru.otus.services.ClientService;
import ru.otus.services.ClientServiceImpl;

public class DbConfig {

    private static final Logger log = LoggerFactory.getLogger(DbConfig.class);

    public static ClientService getClientService(ClientMapper mapper, Configuration configuration) {
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Phone.class,
                Address.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        return new ClientServiceImpl(dbServiceClient, mapper);
    }

    public static void executeMigrations(Configuration configuration) {
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
    }
}
