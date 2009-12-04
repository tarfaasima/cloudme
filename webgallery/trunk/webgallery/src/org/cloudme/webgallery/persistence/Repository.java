package org.cloudme.webgallery.persistence;

import java.util.Collection;

public interface Repository<T> {
    void save(T t);

    Collection<T> findAll();

    T find(long id);

    void delete(long id);
}
