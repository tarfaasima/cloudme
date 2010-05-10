package org.cloudme.triangle.objectify;

import org.cloudme.triangle.Entity;
import org.cloudme.triangle.EntityResolver;

import com.googlecode.objectify.ObjectifyFactory;

public class ObjectifyEntityResolver implements EntityResolver {
    private final ObjectifyFactory factory = new ObjectifyFactory();

    @Override
    public void addEntity(Entity<?> entity) {
        factory.register(entity.getType());
    }

    @Override
    public void put(Object obj) {
        // TODO Auto-generated method stub

    }
}
