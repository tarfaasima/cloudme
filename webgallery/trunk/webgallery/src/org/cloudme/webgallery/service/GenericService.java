package org.cloudme.webgallery.service;

import java.util.Collection;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.persistence.Repository;

public abstract class GenericService<K, T extends IdObject<K>> {
    protected abstract Repository<K, T> getRepository();
    
    public Collection<T> findAll() {
        return getRepository().findAll();
    }

    public T find(K id) {
        return getRepository().find(id);
    }

    public void save(T t) {
        getRepository().save(t);
    }
    
    public void delete(K id) {
        getRepository().delete(id);
    }
}
