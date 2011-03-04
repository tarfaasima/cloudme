package org.cloudme.gaestripes;

import java.util.List;

public abstract class AbstractService<T> {
    private final AbstractDao<T> dao;

    public AbstractService(AbstractDao<T> dao) {
        this.dao = dao;
    }

    public T put(T t) {
        return dao.put(t);
    }

    public T find(Long id) {
        return dao.find(id);
    }

    public Iterable<T> findAll() {
        return dao.findAll();
    }

    public List<T> listAll() {
        return dao.listAll();
    }

    public void delete(Long id) {
        dao.delete(id);
    }
}
