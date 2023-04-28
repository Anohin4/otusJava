package ru.otus.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import ru.otus.services.TemplateProcessor;

public class AdminPageServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "adminPage.html";


    private final TemplateProcessor templateProcessor;

    public AdminPageServlet(TemplateProcessor templateProcessor) {

        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, Collections.emptyMap()));
    }

}
