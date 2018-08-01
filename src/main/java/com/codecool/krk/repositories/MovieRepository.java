package com.codecool.krk.repositories;

import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;

import javax.persistence.EntityTransaction;
import java.util.List;

public class MovieRepository extends Repository <Movie> {

    public MovieRepository() {
        super(Movie.class);
    }

    @Override
    public void update(Movie entity) {
        List<Pornstar> pornstars = entity.getPornstars();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for(Pornstar pornstar : pornstars) {
            em.persist(pornstar);
        }
        em.merge(entity);
        transaction.commit();
    }
}
