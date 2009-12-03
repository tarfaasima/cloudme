package org.cloudme.webgallery;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService<T> {
    @Autowired
    private Repository<T> repository;

    public Collection<T> findAll() {
        return repository.findAll();
    }

    public T find(long id) {
        return repository.find(id);
    }

    public void save(T t) {
        repository.save(t);
    }
}
