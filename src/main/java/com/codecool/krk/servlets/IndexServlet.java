package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.EntityManagerSingleton;
import com.codecool.krk.helpers.StaticFileManager;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.model.User;
import com.codecool.krk.model.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexServlet extends HttpServlet {

    public IndexServlet() {
        populateDb();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String htmlResponse = StaticFileManager.loadFileAsStringByPath(request, "/static/html/index.html");
        response.getWriter().write(htmlResponse);
    }

    private boolean populateDb() {
        EntityManager em = EntityManagerSingleton.getInstance();
        EntityTransaction transaction = em.getTransaction();

        List<Pornstar> pornstarList = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        List<View> views = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<ECategory> categories = new ArrayList<>();

        categories.add(ECategory.BBC);
        categories.add(ECategory.TEEN);

        pornstarList.add(new Pornstar("Marina", "Hantzis", "Sasha Grey", 30, 50, 168, ESex.FEMALE));
        pornstarList.add(new Pornstar("Alexis", "Samper", "Alexis Texas", 33, 59, 173, ESex.FEMALE));
        pornstarList.add(new Pornstar("Jennifer", "Corrales", "Jenna Haze", 36, 45, 160, ESex.FEMALE));
        pornstarList.add(new Pornstar("Elsa", "Jean", "Elsa Jean", 22, 50, 168, ESex.FEMALE));
        pornstarList.add(new Pornstar("Mia", "Khalifa", "Mia Khalifa", 25, 50, 168, ESex.FEMALE));
        pornstarList.add(new Pornstar("Asa", "Takigami", "Asa Akira", 32, 48, 157, ESex.FEMALE));
        pornstarList.add(new Pornstar("Cindy", "Taylor", "Jesse Jane", 38, 49, 160, ESex.FEMALE));

        movies.add(new Movie("Pirates", 129, pornstarList, categories,"https://thumbs.dreamstime.com/z/girl-holding-chicken-brown-35201622.jpg"));
        movies.add(new Movie("Pirates II: Stagnetti's Revenge (2008)", 138, pornstarList, categories, "https://thumbs.dreamstime.com/z/girl-holding-chicken-brown-35201622.jpg"));
        movies.add(new Movie("Titanic Orgy", 129, pornstarList.subList(2,3), categories,"https://thumbs.dreamstime.com/z/girl-holding-chicken-brown-35201622.jpg"));
        movies.add(new Movie("A Tale of Two Titties", 129, pornstarList.subList(3,4), categories,"https://thumbs.dreamstime.com/z/girl-holding-chicken-brown-35201622.jpg"));
        movies.add(new Movie("Buffy the Vampire Layer", 129, pornstarList.subList(4,5), categories,"https://thumbs.dreamstime.com/z/girl-holding-chicken-brown-35201622.jpg"));

        views.add(new View(1, 10));
        views.add(new View(2, 5));
        views.add(new View(3, 7));
        views.add(new View(5, 6));
        views.add(new View(5, 6));

        users.add(new User("edobkowski", "haslo", views));
        users.add(new User("brzozo", "haslo2", views));
        users.add(new User("makiella", "haslo3", views));

        transaction.begin();
        for (Pornstar pornstar : pornstarList) {
            em.persist(pornstar);
        }
        for (Movie movie : movies) {
            em.merge(movie);
        }
        for (View view : views) {
            em.persist(view);q
        }
        for (User user : users) {
            em.persist(user);
        }
        transaction.commit();
        return true;
    }
}
