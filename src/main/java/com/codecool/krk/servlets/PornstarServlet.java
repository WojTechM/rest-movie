package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.EntityManagerSingleton;
import com.codecool.krk.helpers.SelectQueryBuilder;
import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.google.gson.Gson;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PornstarServlet extends HttpServlet {
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
    private void sendSingleJson(HttpServletResponse response, EntityManager em, long id) throws IOException {
        String pornstarJson = em.find(Pornstar.class, id).toJson();
        response.getWriter().write(pornstarJson);
    }

    private void sendAll(HttpServletResponse response, EntityManager em) throws IOException{
        SelectQueryBuilder<Pornstar> selectQueryBuilder = new SelectQueryBuilder<>();

        List<Pornstar> pornstars = selectQueryBuilder.getAll(Pornstar.class);

        Gson gson = new Gson();
        String json =  gson.toJson(pornstars);
        response.getWriter().write(json);
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
