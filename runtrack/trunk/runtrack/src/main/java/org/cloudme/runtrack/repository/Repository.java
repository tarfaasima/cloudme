package org.cloudme.runtrack.repository;

import org.cloudme.runtrack.model.Route;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;

public class Repository<T> extends DAOBase {
    private final Class<T> clazz;

    static {
        register(Route.class);
    }
    
    public Repository(Class<T> clazz) {
        this.clazz = clazz;
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

    private static void register(Class<?>... classes) {
        for (Class<?> c : classes) {
            ObjectifyService.register(c);
        }
    }
}
