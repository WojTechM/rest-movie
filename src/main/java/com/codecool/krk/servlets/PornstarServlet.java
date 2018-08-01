package com.codecool.krk.servlets;

import com.codecool.krk.enums.ESex;
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

public class PornstarServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pornstar pornstar = new Pornstar("Sasha", "Grey", "Sasha", 29, 50, 160, ESex.FEMALE);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("porndatabasePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(pornstar);
        transaction.commit();

        String htmlResponse = (em.find(Pornstar.class, 1L)).toJson();

        response.getWriter().write(htmlResponse);
    }
}
