package com.codecool.krk.servlets;

import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.View;
import com.codecool.krk.repositories.Repository;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewServlet extends HttpServlet {
    private Repository<View> viewRepository = new Repository<>(View.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            sendSingleJson(response, id);
        } else {
            sendAll(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        View view = loadViewFromRequest(request);
        viewRepository.add(view);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        View view = loadViewFromRequest(request);
        viewRepository.update(view);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        View view;
        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            view = viewRepository.get(id);
        } else {
            view = loadViewFromRequest(request);
        }
        viewRepository.delete(view);
    }

    private void sendSingleJson(HttpServletResponse response, long id) throws IOException {
        String json;
        try {
            json = viewRepository.get(id).toJson();
        } catch (NullPointerException e) {
            json = "Entity not found";
        }
        response.getWriter().write(json);
    }

    private void sendAll(HttpServletResponse response) throws IOException{
        List<View> views = viewRepository.getAll();
        Gson gson = new Gson();
        String json =  gson.toJson(views);
        response.getWriter().write(json);
    }

    private View loadViewFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        request.getReader().lines().forEach(jsonLine -> jsonBuilder.append(jsonLine));
        return new Gson().fromJson(jsonBuilder.toString(), View.class);
    }
}

