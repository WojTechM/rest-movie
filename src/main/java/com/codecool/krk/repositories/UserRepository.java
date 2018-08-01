package com.codecool.krk.repositories;

import com.codecool.krk.model.User;
import com.codecool.krk.model.View;

import javax.persistence.EntityTransaction;
import java.util.List;

public class UserRepository extends Repository <User> {

    public UserRepository() {
        super(User.class);
    }

    @Override
    public void update(User entity) {
        List<View> views = entity.getViews();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for(View view : views) {
            em.persist(view);
        }
        em.merge(entity);
        transaction.commit();
    }
}
