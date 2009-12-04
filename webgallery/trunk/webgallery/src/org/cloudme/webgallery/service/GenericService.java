package org.cloudme.webgallery.service;

import java.util.Collection;

import org.cloudme.webgallery.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericService<T> {
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
    
    public void delete(long id) {
        repository.delete(id);
    }
}
