package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;

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

public class MovieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String htmlResponse;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("porndatabasePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Movie movie = new Movie(new ArrayList<Pornstar>(), new ArrayList<ECategory>(), 120);
        em.persist(movie);
        transaction.commit();

        htmlResponse = (em.find(Movie.class, 1l)).toJson();

        response.getWriter().write(htmlResponse);
    }

}
