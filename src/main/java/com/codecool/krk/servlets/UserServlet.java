package com.codecool.krk.servlets;

import com.codecool.krk.helpers.EntityManagerSingleton;
import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.User;
import com.codecool.krk.repositories.Repository;
import com.codecool.krk.repositories.UserRepository;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private Repository<User> userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EntityManagerSingleton.getInstance();

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
        User user = loadUserFromHttpRequest(request);
        userRepository.add(user);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = loadUserFromHttpRequest(request);
        userRepository.update(user);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        User user;
        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            user = userRepository.get(id);
        } else {
            user = loadUserFromHttpRequest(request);
        }
        userRepository.delete(user);
    }

    private void sendSingleJson(HttpServletResponse response, long id) throws IOException {
        String userJson = userRepository.get(id).toJson();
        response.getWriter().write(userJson);
    }

    private void sendAll(HttpServletResponse response) throws IOException{
        List<User> users = userRepository.getAll();
        Gson gson = new Gson();
        String json =  gson.toJson(users);
        response.getWriter().write(json);
    }

    private User loadUserFromHttpRequest(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        request.getReader().lines().forEach(jsonLine -> jsonBuilder.append(jsonLine));
        return new Gson().fromJson(jsonBuilder.toString(), User.class);
    }
}
