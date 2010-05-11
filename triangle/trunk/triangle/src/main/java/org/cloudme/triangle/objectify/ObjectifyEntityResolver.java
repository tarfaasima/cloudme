package org.cloudme.triangle.objectify;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import org.cloudme.triangle.Entity;
import org.cloudme.triangle.EntityResolver;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

public class ObjectifyEntityResolver implements EntityResolver {
    private final ObjectifyFactory factory = new ObjectifyFactory();

    @Override
    public void addEntity(Entity<?> entity) {
        factory.register(entity.getType());
    }

    @Override
    public void put(Object obj) {
        final Objectify ofy = factory.beginTransaction();
        ofy.put(obj);
        ofy.getTxn().commit();
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> T get(Entity<?> entity, long id) {
        return factory.begin().get((Class<? extends T>) entity.getType(), id);
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> Collection<T> findAll(Entity<T> entity) {
        final Query<T> query = (Query<T>) factory.begin().query(entity.getType());
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

    @Override
    public <T> void delete(Entity<T> entity, long id) {
        final Objectify ofy = factory.beginTransaction();
        ofy.delete(entity.getType(), id);
        ofy.getTxn().commit();
    }
}
