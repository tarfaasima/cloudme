package org.cloudme.webgallery.persistence;

import java.util.Collection;

public interface Repository<T> {
    void save(T t);

    Collection<T> findAll();

    T find(String id);

    void delete(String id);
}
