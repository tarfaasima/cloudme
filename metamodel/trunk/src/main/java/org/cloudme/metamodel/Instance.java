package org.cloudme.metamodel;

import java.util.Collection;

import org.cloudme.metamodel.util.ValidationError;

/**
 * Represents an {@link Instance} of a metamodel {@link Entity}.
 * <p>
 * Use {@link Entity#createInstance()} to create a new instance of an
 * {@link Entity}.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 */
public interface Instance {
    /**
     * Sets the value of the property with the given name.
     * 
     * @param name
     *            The name of the property.
     * @param value
     *            The new value.
     */
    void setValue(String name, String value);

    /**
     * Validates this {@link Instance} according to the {@link Entity}
     * declaration.
     * 
     * @return A collection of errors, never {@code null}. If this
     *         {@link Instance} is valid, an empty collection is returned.
     */
    Collection<ValidationError> validate();
}
