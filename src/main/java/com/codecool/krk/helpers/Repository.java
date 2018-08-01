package com.codecool.krk.helpers;

import com.codecool.krk.helpers.EntityManagerSingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Repository <E> {
    private EntityManager em = EntityManagerSingleton.getInstance();
    private Class ofClass;

    public Repository(Class ofClass) {
        this.ofClass = ofClass;
    }

    public void persistEntity(E entity) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(entity);
        transaction.commit();
    }

    public void delete(E entity) {

    }

    public E get(long id) {
        return (E) em.find(ofClass, id);
    }

    public List<E> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = cb.createQuery(ofClass);
        Root<E> root = criteriaQuery.from(ofClass);
        criteriaQuery.select(root);
        TypedQuery<E> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
