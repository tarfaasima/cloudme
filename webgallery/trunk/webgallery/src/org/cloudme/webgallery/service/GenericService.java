package org.cloudme.webgallery.service;

import java.util.Collection;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericService<K, T extends IdObject<K>> {
    @Autowired
    private Repository<K, T> repository;

    public Collection<T> findAll() {
        return repository.findAll();
    }

    public T find(K id) {
        return repository.find(id);
    }

    public void save(T t) {
        repository.save(t);
    }
    
    public void delete(K id) {
        repository.delete(id);
    }
}
