package org.cloudme.runtrack.repository;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import org.cloudme.runtrack.model.Route;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
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
        for (int i = 0; i < classes.length; i++) {
            ObjectifyService.register(classes[i]);
        }
    }

    public Collection<T> findAll() {
        final Query<T> query = ofy().query(clazz);
        return new AbstractCollection<T>() {
            @Override
            public Iterator<T> iterator() {
                return query.iterator();
            }

            @Override
            public int size() {
                return query.countAll();
            }
        };
    }
}
