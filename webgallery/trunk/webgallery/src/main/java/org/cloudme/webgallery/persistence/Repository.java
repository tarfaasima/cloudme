package org.cloudme.webgallery.persistence;

import java.util.Collection;

import org.cloudme.webgallery.model.IdObject;

public interface Repository<K, T extends IdObject<K>> {
    void save(T t);

    Collection<T> findAll();

    T find(K id);

    void delete(K id);
}
