package jnpp.dao.repositories;

import java.io.Serializable;

public interface IGenericDAO<T extends Serializable> {
    
    T save(T t);
    T update(T t);
    void delete(T t);
    T find(Object id);
    
}
