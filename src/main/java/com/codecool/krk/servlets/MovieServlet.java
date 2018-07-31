package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.EntityManagerSingleton;
import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        populateDb();
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            sendSingleJson(response, id);
        } else {
            sendAll(response);
        }
    }

    private void sendSingleJson(HttpServletResponse response, long id) throws IOException {
        EntityManager em = EntityManagerSingleton.getInstance();
        response.getWriter().write(em.find(Movie.class, id).toJson());
    }

    private void sendAll(HttpServletResponse response) throws IOException{
        EntityManager em = EntityManagerSingleton.getInstance();
        Query query = em.createQuery("SELECT m FROM Movie m");
        List<Movie> movies = query.getResultList();
        Gson gson = new Gson();
        String json =  gson.toJson(movies);
        response.getWriter().write(json);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            updateMovie(request, id);
        }
    }

    private void updateMovie(HttpServletRequest request, long id) {
        EntityManager em = EntityManagerSingleton.getInstance();
        Movie movie = em.find(Movie.class, id);

        movie.setTitle(getTitleFromRequest(request));
        movie.setDuration(getDurationFromRequest(request));
        movie.setCategories(getCategoriesFromRequest(request));
        movie.setPornstars(getPornstarsFromRequest(request));

        persistMovie(movie);
    }

    private String getTitleFromRequest(HttpServletRequest request) {
        return request.getParameter("title");
    }

    private float getDurationFromRequest(HttpServletRequest request) {
        return Float.parseFloat(request.getParameter("duration"));
    }

    private List<ECategory> getCategoriesFromRequest(HttpServletRequest request) {
        String[] categoriesAsStrings = request.getParameterValues("category");
        List<ECategory> categories = new ArrayList<>();
        for(String s : categoriesAsStrings) {
            categories.add(ECategory.valueOf(s.toUpperCase()));
        }
        return categories;
    }

    private List<Pornstar> getPornstarsFromRequest(HttpServletRequest request) {
        String[] pornstarJsons = request.getParameterValues("pornstar");
        List<Pornstar> pornstars = new ArrayList<>();
        Gson gson = new Gson();
        for(String s : pornstarJsons) {
            pornstars.add(gson.fromJson(s, Pornstar.class));
        }
        return pornstars;
    }

    private void persistMovie(Movie movie) {
        EntityManager em = EntityManagerSingleton.getInstance();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(movie);
        transaction.commit();
    }

    private void populateDb() {
        EntityManager em = EntityManagerSingleton.getInstance();
        EntityTransaction transaction = em.getTransaction();
        List<Pornstar> stars = new ArrayList<>();
        Pornstar star = new Pornstar("Sasha", "Grey", "Sasha Grey", 29, 50, 160, ESex.FEMALE);

        List<ECategory> categories = new ArrayList<>();
        categories.add(ECategory.BBC);
        categories.add(ECategory.TEEN);
        Movie movie = new Movie("Pirates II: Stagnetti's Revenge (2008)", 138, stars, categories);

        stars.add(star);
        transaction.begin();
        em.persist(star);
        em.persist(movie);
        transaction.commit();
    }
}
