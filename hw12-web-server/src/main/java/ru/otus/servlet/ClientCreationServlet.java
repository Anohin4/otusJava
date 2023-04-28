package ru.otus.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ru.otus.model.dto.ClientDto;
import ru.otus.services.ClientService;
import ru.otus.services.TemplateProcessor;

public class ClientCreationServlet extends HttpServlet {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_PHONE = "phone";
    private static final String PARAM_ADDRESS = "address";
    private static final String NEW_USER_PARAM = "newUser";
    private static final String PAGE_TEMPLATE = "okCreation.html";
    private static final String LOGIN_PAGE_TEMPLATE = "createClient.html";


    private final TemplateProcessor templateProcessor;
    private final ClientService clientService;

    public ClientCreationServlet(TemplateProcessor templateProcessor,
            ClientService clientService) {
        this.clientService = clientService;

        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, Collections.emptyMap()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(PARAM_NAME);
        String street = request.getParameter(PARAM_ADDRESS);
        String phone = request.getParameter(PARAM_PHONE);
        ClientDto client = new ClientDto(name, street, phone);
        ClientDto savedClient = clientService.saveClient(client);

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(NEW_USER_PARAM, savedClient);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(PAGE_TEMPLATE, paramsMap));

    }

}
