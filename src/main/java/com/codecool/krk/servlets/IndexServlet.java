package com.codecool.krk.servlets;

import com.codecool.krk.helpers.StaticFileManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String htmlResponse = StaticFileManager.loadFileAsStringByPath(request, "/static/html/index.html");
        response.getWriter().write(htmlResponse);

    }
}
