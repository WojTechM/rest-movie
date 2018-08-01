package com.codecool.krk.servlets;

import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.EntityManagerSingleton;
import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.repositories.Repository;
import com.google.gson.Gson;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PornstarServlet extends HttpServlet {
    private Repository<Pornstar> pornstarRepository = new Repository<>(Pornstar.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EntityManagerSingleton.getInstance();
        populateDb(em);

        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            sendSingleJson(response, em, id);
        } else {
            sendAll(response, em);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pornstar pornstar = new Pornstar();
        setData(request, pornstar);
        pornstarRepository.add(pornstar);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            Pornstar updatedPornstar = pornstarRepository.get(id);
            setData(request, updatedPornstar);
            pornstarRepository.update(updatedPornstar);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            Pornstar pornstar = pornstarRepository.get(id);
            pornstarRepository.delete(pornstar);
        }
    }

    private void sendSingleJson(HttpServletResponse response, EntityManager em, long id) throws IOException {
        String pornstarJson = pornstarRepository.get(id).toJson();
        response.getWriter().write(pornstarJson);
    }

    private void sendAll(HttpServletResponse response, EntityManager em) throws IOException{

        List<Pornstar> pornstars = pornstarRepository.getAll();

        Gson gson = new Gson();
        String json =  gson.toJson(pornstars);
        response.getWriter().write(json);
    }

    private Pornstar setData(HttpServletRequest request, Pornstar pornstar) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String nickName = request.getParameter("nickName");
        long age = Long.parseLong(request.getParameter("age"));
        long weight = Long.parseLong(request.getParameter("weight"));
        long height = Long.parseLong(request.getParameter("height"));
        ESex sex = ESex.valueOf(request.getParameter("sex").toUpperCase());

        pornstar.setFirstName(firstName);
        pornstar.setLastName(lastName);
        pornstar.setNickName(nickName);
        pornstar.setAge(age);
        pornstar.setWeight(weight);
        pornstar.setHeight(height);
        pornstar.setSex(sex);

        return pornstar;
    }

    private void populateDb(EntityManager em) {
        EntityTransaction transaction = em.getTransaction();
        List<Pornstar> pornstars = new ArrayList<>();
        pornstars.add(new Pornstar("Sasha", "Grey", "Sasha Grey", 29, 50, 160, ESex.FEMALE));
        pornstars.add(new Pornstar("Alexis", "Samper", "Alexis Texas", 33, 59, 173, ESex.FEMALE));
        pornstars.add(new Pornstar("Jennifer", "Corrales", "Jenna Haze", 36, 45, 160, ESex.FEMALE));

        transaction.begin();
        for(Pornstar pornstar : pornstars) {
            em.persist(pornstar);
        }
        transaction.commit();
    }
}
