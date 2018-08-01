package com.codecool.krk.servlets;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.enums.ESex;
import com.codecool.krk.helpers.EntityManagerSingleton;
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
import java.util.List;

public class PornstarServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        populateDb();
    }

    private void populateDb() {
        EntityManager em = EntityManagerSingleton.getInstance();
        EntityTransaction transaction = em.getTransaction();
        Pornstar pornstar = new Pornstar("Sasha", "Grey", "Sasha Grey", 29, 50, 160, ESex.FEMALE);

        transaction.begin();
        em.persist(pornstar);
        transaction.commit();
    }
}
