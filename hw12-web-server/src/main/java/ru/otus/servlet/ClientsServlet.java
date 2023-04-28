package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.model.dto.ClientDto;
import ru.otus.services.ClientService;
import ru.otus.services.TemplateProcessor;


public class ClientsServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "clients";

    private final ClientService clientService;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, ClientService clientService) {
        this.templateProcessor = templateProcessor;
        this.clientService = clientService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<ClientDto> all = clientService.findAll();
        paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, all);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
