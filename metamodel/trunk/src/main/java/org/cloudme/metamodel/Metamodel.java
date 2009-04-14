package org.cloudme.metamodel;

import java.util.Collection;

/**
 * This class represents a metamodel. This class is used to create new
 * {@link Entity}s.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 */
public interface Metamodel {
    /**
     * Returns all entities of this {@link Metamodel}.
     * 
     * @return All {@link Entity} declarations.
     */
    public Collection<Entity> getEntities();

    /**
     * Creates a new {@link Entity} with the given name.
     * 
     * @param name
     *            The name of the {@link Entity}.
     * @return The new {@link Entity}.
     */
    public Entity newEntity(String name);
}
