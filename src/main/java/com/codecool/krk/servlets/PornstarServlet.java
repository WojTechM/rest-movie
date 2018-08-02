package com.codecool.krk.servlets;

import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.repositories.Repository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PornstarServlet extends HttpServlet {
    private Repository<Pornstar> pornstarRepository = new Repository<>(Pornstar.class);

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
        Pornstar pornstar = loadPornstarFromRequest(request);
        pornstarRepository.add(pornstar);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pornstar pornstar = loadPornstarFromRequest(request);
        pornstarRepository.update(pornstar);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());
        Pornstar pornstar;
        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            pornstar = pornstarRepository.get(id);
        } else {
            pornstar = loadPornstarFromRequest(request);
        }
        pornstarRepository.delete(pornstar);
    }

    private void sendSingleJson(HttpServletResponse response, long id) throws IOException {
        String pornstarJson = pornstarRepository.get(id).toJson();
        response.getWriter().write(pornstarJson);
    }

    private void sendAll(HttpServletResponse response) throws IOException{

        List<Pornstar> pornstars = pornstarRepository.getAll();

        Gson gson = new Gson();
        String json =  gson.toJson(pornstars);
        response.getWriter().write(json);
    }

    private Pornstar loadPornstarFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        request.getReader().lines().forEach(jsonLine -> jsonBuilder.append(jsonLine));
        return new Gson().fromJson(jsonBuilder.toString(), Pornstar.class);
    }
}
