package ru.otus.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import ru.otus.services.TemplateProcessor;

public class UserPageServlet extends HttpServlet {

    private static final String USER_PAGE_TEMPLATE = "userPage.html";


    private final TemplateProcessor templateProcessor;

    public UserPageServlet(TemplateProcessor templateProcessor) {

        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, Collections.emptyMap()));
    }

}
