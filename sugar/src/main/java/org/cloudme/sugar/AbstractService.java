package org.cloudme.sugar;

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

    public T find(Id<T, Long> id) {
        return find(id.value());
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

    public void delete(Id<T, Long> id) {
        delete(id.value());
    }
}
