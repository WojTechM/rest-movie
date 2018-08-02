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

        List<ECategory> categories = generateCategories();
        List<Pornstar> pornstarList = generatePornstars();
        List<Movie> movies = generateMovies(pornstarList, categories);
        List<View> views = new ArrayList<>();
        List<View> views2 = new ArrayList<>();
        List<View> views3 = new ArrayList<>();
        List<User> users = new ArrayList<>();

        views.add(new View(1, 10));
        views.add(new View(2, 1));

        views2.add(new View(3, 10));

        views3.add(new View(4, 10));

        users.add(new User("edobkowski", "haslo", views));
        users.add(new User("brzozo", "haslo2", views2));
        users.add(new User("makiella", "haslo3", views3));

        transaction.begin();
        for (Pornstar pornstar : pornstarList) {
            em.persist(pornstar);
        }
        for (Movie movie : movies) {
            em.merge(movie);
        }
        for (View view : views) {
            em.persist(view);
        }
        for (View view : views2) {
            em.persist(view);
        }
        for (View view : views3) {
            em.persist(view);
        }
        for (User user : users) {
            em.persist(user);
        }
        transaction.commit();
        return true;
    }

    private List<Pornstar> generatePornstars() {
        List<Pornstar> pornstarList = new ArrayList<>();
        pornstarList.add(new Pornstar("Marina", "Hantzis", "Sasha Grey", 30, 50, 168, ESex.FEMALE));
        pornstarList.add(new Pornstar("Alexis", "Samper", "Alexis Texas", 33, 59, 173, ESex.FEMALE));
        pornstarList.add(new Pornstar("Jennifer", "Corrales", "Jenna Haze", 36, 45, 160, ESex.FEMALE));
        pornstarList.add(new Pornstar("Elsa", "Jean", "Elsa Jean", 22, 50, 168, ESex.FEMALE));
        pornstarList.add(new Pornstar("Mia", "Khalifa", "Mia Khalifa", 25, 50, 168, ESex.FEMALE));
        pornstarList.add(new Pornstar("Asa", "Takigami", "Asa Akira", 32, 48, 157, ESex.FEMALE));
        pornstarList.add(new Pornstar("Cindy", "Taylor", "Jesse Jane", 38, 49, 160, ESex.FEMALE));

        return pornstarList;
    }

    private List<Movie> generateMovies(List<Pornstar> pornstarList, List<ECategory> categories) {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Pirates", 129, pornstarList.subList(0,2), categories.subList(0,4),"https://www.costumes.com.au/media/catalog/product/cache/10/image/5e06319eda06f020e43594a9c230972d/8/0/800263/Pirates-Of-The-Caribbean---Angelica-Deluxe-Adult-Women-s-Costume--Disguise-BSDS-800263-31.jpg"));
        movies.add(new Movie("Pirates II: Stagnetti's Revenge (2008)", 138, pornstarList.subList(0,3), categories.subList(4,8), "https://media.takealot.com/covers_tsins/31871943/31871943-1-zoom.jpeg"));
        movies.add(new Movie("Titanic Orgy", 129, pornstarList.subList(2,3), categories.subList(2,6),"https://i.ytimg.com/vi/erAQ9LkftwA/maxresdefault.jpg"));
        movies.add(new Movie("A Tale of Two Titties", 129, pornstarList.subList(3,4), categories,"https://i1.jbzdy.pl/contents/2017/09/0d7aae952c756c4973a30099e8d45fb4.jpg"));
        movies.add(new Movie("Buffy the Vampire Layer", 129, pornstarList, categories,"https://a.wattpad.com/cover/82993284-352-k403726.jpg"));

        return movies;
    }

    private List<ECategory> generateCategories() {
        List<ECategory> categories = new ArrayList<>();
        categories.add(ECategory.BBC);
        categories.add(ECategory.TEEN);
        categories.add(ECategory.AMATEUR);
        categories.add(ECategory.ARAB);
        categories.add(ECategory.ASIAN);
        categories.add(ECategory.BDSM);
        categories.add(ECategory.BLONDE);
        categories.add(ECategory.BJ);
        categories.add(ECategory.COMPILATION);

        return categories;
    }
}
