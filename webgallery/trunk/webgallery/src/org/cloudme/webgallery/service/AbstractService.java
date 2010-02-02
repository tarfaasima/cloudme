package org.cloudme.webgallery.service;

import java.util.Collection;

import org.cloudme.webgallery.model.IdObject;
import org.cloudme.webgallery.persistence.Repository;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService<K, T extends IdObject<K>> {
	private final Repository<K, T> repository;
    
    protected AbstractService(Repository<K, T> repository) {
        this.repository = repository;
    }

    protected Repository<K, T> getRepository() {
        return repository;
    }

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
