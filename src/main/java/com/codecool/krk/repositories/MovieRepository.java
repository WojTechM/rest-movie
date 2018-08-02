package com.codecool.krk.repositories;

import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;

import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.util.List;

public class MovieRepository extends Repository <Movie> {

    public MovieRepository() {
        super(Movie.class);
    }

    @Override
    public void update(Movie entity) {
        List<Pornstar> pornstars = entity.getPornstars();
        savePornstars(pornstars);
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        } catch (RollbackException e) {
            transaction.rollback();
        }
    }

    private void savePornstars(List<Pornstar> pornstars) {
        EntityTransaction transaction = em.getTransaction();
        for(Pornstar pornstar : pornstars) {
            try {
                transaction.begin();
                em.persist(pornstar);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                transaction.begin();
                em.merge(pornstar);
                transaction.commit();
            }
        }
    }
}
