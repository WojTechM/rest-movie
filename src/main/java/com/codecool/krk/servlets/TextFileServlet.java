package com.codecool.krk.servlets;

import com.codecool.krk.helpers.StaticFileManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TextFileServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        String htmlResponse = StaticFileManager.loadFileAsStringByPath(request, uri);
        response.getWriter().write(htmlResponse);
    }
}