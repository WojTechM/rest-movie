package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.EntityManagerSingleton;
import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.model.User;
import com.codecool.krk.model.View;
import com.codecool.krk.repositories.Repository;
import com.codecool.krk.repositories.UserRepository;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServlet extends HttpServlet {
    private Repository<User> userRepository = new UserRepository();

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

    private void sendSingleJson(HttpServletResponse response, EntityManager em, long id) throws IOException {
        String userJson = userRepository.get(id).toJson();
        response.getWriter().write(userJson);
    }

    private void sendAll(HttpServletResponse response, EntityManager em) throws IOException{

        List<User> users = userRepository.getAll();

        Gson gson = new Gson();
        String json =  gson.toJson(users);
        response.getWriter().write(json);
    }

    private void populateDb(EntityManager em) {
        EntityTransaction transaction = em.getTransaction();
        List<User> users = new ArrayList<>();
        List<Pornstar> stars = new ArrayList<>();
        Pornstar star = new Pornstar("Sasha", "Grey", "Sasha Grey", 29, 50, 160, ESex.FEMALE);

        List<ECategory> categories = new ArrayList<>();
        categories.add(ECategory.BBC);
        categories.add(ECategory.TEEN);
        Repository<Movie> movieRepository = new Repository<>(Movie.class);
        Movie movie = movieRepository.get(1);
        List<View> views = new ArrayList<>();
        View view = new View(movie.getId(), 10);
        views.add(view);
        users.add(new User("edobkowski", "haslo", views));
        users.add(new User("brzozo", "haslo2"));
        users.add(new User("makiella", "haslo3"));

        transaction.begin();
        em.persist(star);
        em.persist(movie);
        em.persist(view);
        for(User user : users) {
            em.persist(user);
        }
        transaction.commit();
    }
}
