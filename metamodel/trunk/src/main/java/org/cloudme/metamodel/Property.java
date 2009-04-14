package org.cloudme.metamodel;

/**
 * A property of an {@link Entity}.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 */
public interface Property {
    /**
     * The name of the {@link Property}.
     * 
     * @return The name of the {@link Property}.
     */
    public String getName();

    /**
     * The {@link Type} of the {@link Property}.
     * 
     * @return The {@link Type} of the {@link Property}.
     */
    public Type getType();

    /**
     * The label of the {@link Property}.
     * 
     * @return The label of the {@link Property}.
     */
    public String getLabel();
}
