package com.codecool.krk.helpers;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SelectQueryBuilder <E> {
    private EntityManager em = EntityManagerSingleton.getInstance();

    public List<E> getAll(Class<E> ofClass) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = cb.createQuery(ofClass);
        Root<E> root = criteriaQuery.from(ofClass);
        criteriaQuery.select(root);
        TypedQuery<E> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
