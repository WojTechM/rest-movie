package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.EntityManagerSingleton;
import com.codecool.krk.repositories.MovieRepository;
import com.codecool.krk.repositories.Repository;
import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MovieServlet extends HttpServlet {
    private Repository<Movie> movieRepository = new MovieRepository();

    @Override
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String title = getTitleFromRequest(request);
        float duration = getDurationFromRequest(request);
        List<ECategory> categories = getCategoriesFromRequest(request);
        List<Pornstar> pornstars = getPornstarsFromRequest(request);

        Movie movie = new Movie(title, duration, pornstars, categories, "https://thumbs.dreamstime.com/z/girl-holding-chicken-brown-35201622.jpg");

        movieRepository.add(movie);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Movie movie = updateMovie(request);
        movieRepository.update(movie);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            Movie movie = movieRepository.get(id);
            movieRepository.delete(movie);
        }
    }

    private void sendSingleJson(HttpServletResponse response, long id) throws IOException {
        String movieJson = movieRepository.get(id).toJson();
        response.getWriter().write(movieJson);
    }

    private void sendAll(HttpServletResponse response) throws IOException{
        List<Movie> movies = movieRepository.getAll();

        Gson gson = new Gson();
        String json =  gson.toJson(movies);
        response.getWriter().write(json);
    }

    private Movie updateMovie(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        request.getReader().lines().forEach(jsonLine -> jsonBuilder.append(jsonLine));
        return new Gson().fromJson(jsonBuilder.toString(), Movie.class);
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

        if(categoriesAsStrings != null) {
            for(String s : categoriesAsStrings) {
                categories.add(ECategory.valueOf(s.toUpperCase()));
            }
        }
        return categories;
    }

    private List<Pornstar> getPornstarsFromRequest(HttpServletRequest request) {
        String[] pornstarJsons = request.getParameterValues("pornstar");
        List<Pornstar> pornstars = new ArrayList<>();
        Gson gson = new Gson();
        if (pornstarJsons != null) {
            for (String s : pornstarJsons) {
                pornstars.add(gson.fromJson(s, Pornstar.class));
            }
        }
        return pornstars;
    }

    private void populateDb() {
        EntityManager em = EntityManagerSingleton.getInstance();
        EntityTransaction transaction = em.getTransaction();
        List<Pornstar> stars = new ArrayList<>();
        Pornstar star = new Pornstar("Sasha", "Grey", "Sasha Grey", 29, 50, 160, ESex.FEMALE);

        List<ECategory> categories = new ArrayList<>();
        categories.add(ECategory.BBC);
        categories.add(ECategory.TEEN);
        Movie movie = new Movie("Pirates II: Stagnetti's Revenge (2008)", 138, stars, categories, "https://thumbs.dreamstime.com/z/girl-holding-chicken-brown-35201622.jpg");

        stars.add(star);
        transaction.begin();
        em.persist(star);
        em.persist(movie);
        transaction.commit();
    }
}
