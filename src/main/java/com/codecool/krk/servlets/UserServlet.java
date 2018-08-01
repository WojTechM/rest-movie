package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.model.User;
import com.codecool.krk.model.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Pornstar> pornstarList = new ArrayList<>();
        Pornstar pornstar = new Pornstar("Sasha", "Grey", "Sasha", 29, 50, 160, ESex.FEMALE);
        pornstarList.add(pornstar);
        Movie movie = new Movie("Pirates II: Stagnetti's Revenge (2008)", 138, pornstarList, new ArrayList<ECategory>());
        List<View> views = new ArrayList<>();
        View view = new View(movie, 10);
        views.add(view);
        User user = new User("us", "pass", views);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("porndatabasePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(pornstar);
        em.persist(movie);
        em.persist(view);
        em.persist(user);
        transaction.commit();

        String htmlResponse = (em.find(User .class, 1L)).toJson();

        response.getWriter().write(htmlResponse);
    }
}
