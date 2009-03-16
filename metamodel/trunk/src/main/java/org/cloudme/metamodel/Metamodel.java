package org.cloudme.metamodel;

import java.util.Collection;

public interface Metamodel {
    public Collection<Entity> getEntities();
    
    public Entity newEntity(String string);
}
