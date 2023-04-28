package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import org.mapstruct.factory.Mappers;
import ru.otus.config.DbConfig;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.mapping.ClientMapper;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerWithBasicSecurity;
import ru.otus.services.ClientService;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        ClientMapper mapper = Mappers.getMapper(ClientMapper.class);
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        DbConfig.executeMigrations(configuration);
        ClientService clientService = DbConfig.getClientService(mapper, configuration);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(
                HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, clientService, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
