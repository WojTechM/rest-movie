package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.Repository;
import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.model.User;
import com.codecool.krk.model.View;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewServlet extends HttpServlet {
    private Repository<View> viewRepository = new Repository<>(View.class);
    private boolean databasePopulated = populateDb();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            sendSingleJson(response, id);
        } else {
            sendAll(response);
        }
    }

    private void sendSingleJson(HttpServletResponse response, long id) throws IOException {
        String viewJson = viewRepository.get(id).toJson();
        response.getWriter().write(viewJson);
    }

    private void sendAll(HttpServletResponse response) throws IOException{
        List<View> views = viewRepository.getAll();

        Gson gson = new Gson();
        String json =  gson.toJson(views);
        response.getWriter().write(json);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            View view = viewRepository.get(id);
            viewRepository.delete(view);
        }
    }

    private boolean populateDb() {
        List<Pornstar> pornstarList = new ArrayList<>();
        Pornstar pornstar = new Pornstar("Sasha", "Grey", "Sasha", 29, 50, 160, ESex.FEMALE);
        pornstarList.add(pornstar);
        Movie movie = new Movie("Pirates II: Stagnetti's Revenge (2008)", 138, pornstarList, new ArrayList<ECategory>());
        List<View> views = new ArrayList<>();
        View view = new View(movie, 10);
        views.add(view);
        User user = new User("login", "password", views);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("porndatabasePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(pornstar);
        em.persist(movie);
        em.persist(view);
        em.persist(user);
        transaction.commit();
        return true;
    }
}

