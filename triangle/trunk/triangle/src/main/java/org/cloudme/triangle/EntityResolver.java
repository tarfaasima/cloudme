package org.cloudme.triangle;

/**
 * An {@link EntityResolver} provides methods to the {@link TriangleController}
 * to resolve entities from the persistence facility. It is replaceable to allow
 * different implementations and persistence technologies, such as Google App
 * Engine datastore, Hibernate, File, XML etc.
 * 
 * @author Moritz Petersen
 */
public interface EntityResolver {
    /**
     * Adds the given {@link Entity} declaration to this resolver.
     * 
     * @param entity
     *            The {@link Entity} declaration.
     */
    void addEntity(Entity entity);
}
