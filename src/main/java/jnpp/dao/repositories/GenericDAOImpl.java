package jnpp.dao.repositories;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

public abstract class GenericDAOImpl<T extends Serializable>
        implements GenericDAO<T> {

    @PersistenceContext(unitName = "JNPPPU")
    private EntityManager em;

    private Class<T> type;

    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        type = (Class<T>) ((ParameterizedType) t).getActualTypeArguments()[0];
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public T save(T t) {
        t = em.merge(t);
        em.persist(t);
        return t;
    }

    @Transactional
    @Override
    public T update(T t) {
        em.merge(t);
        return t;
    }

    @Transactional
    @Override
    public void delete(T t) {
        t = em.merge(t);
        em.remove(t);
    }

    @Transactional
    @Override
    public T find(Object id) {
        return em.find(type, id);
    }

}
