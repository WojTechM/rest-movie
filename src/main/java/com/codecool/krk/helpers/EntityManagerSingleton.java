package com.codecool.krk.helpers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

    private static EntityManager instance;

    private EntityManagerSingleton() {}

    public static EntityManager getInstance() {
        if (instance == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("porndatabasePU");
            instance = emf.createEntityManager();
        }
        return instance;
    }
}
