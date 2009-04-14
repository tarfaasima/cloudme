package org.cloudme.metamodel;

import java.util.Collection;

/**
 * Declaration of a metamodel {@link Entity}. An {@link Entity} can have
 * multiple {@link Property}, defined by the
 * {@link #setProperty(String, Type, String)} method.
 * <p>
 * The method {@link #getProperties()} returns a collection of all properties.
 * <p>
 * To create an {@link Instance} of this {@link Entity}, call the
 * {@link #createInstance()} method.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 */
public interface Entity {
    /**
     * Creates a new {@link Instance} of this {@link Entity}.
     * 
     * @return A new {@link Instance}.
     */
    Instance createInstance();
    
    /**
     * Returns the name of this {@link Entity}.
     * 
     * @return The name.
     */
    String getName();
    
    /**
     * Returns a list of all {@link Property}s.
     * 
     * @return The properties of this {@link Entity}.
     */
    Collection<Property> getProperties();

    /**
     * Sets one property.
     * 
     * @param name The name of the property.
     * @param type The type of the property.
     * @param label The label of the property.
     */
    void setProperty(String name, Type type, String label);
}
