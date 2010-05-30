package org.cloudme.runtrack.repository;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;

@Deprecated
public class Repository<T> extends DAOBase {
    private final Class<T> clazz;
    
    public Repository(Class<T> clazz) {
        this.clazz = clazz;
        ObjectifyService.register(clazz);
    }

    public T find(long id) {
        return ofy().find(clazz, id);
    }

    public void put(T t) {
        ofy().put(t);
    }

    public void delete(long id) {
        ofy().delete(clazz, id);
    }
}
