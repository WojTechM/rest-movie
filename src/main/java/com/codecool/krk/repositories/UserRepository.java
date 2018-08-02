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
        saveViews(views);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        transaction.commit();
    }

    private void saveViews(List<View> views) {
        EntityTransaction transaction = em.getTransaction();
        for(View view : views) {
            try {
                transaction.begin();
                em.persist(view);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                transaction.begin();
                em.merge(view);
                transaction.commit();
            }
        }
    }
}
