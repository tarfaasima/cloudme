package org.cloudme.metamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * An instance of a {@link Type} in the metamodel.
 * 
 * @author Moritz Petersen, moritz@cloudme.org
 */
public class Instance {
    /**
     * The type of this instance.
     */
    private final Type type;
    /**
     * The map containing all values of this instance.
     */
    private final Map<Attribute, String> valueMap = new HashMap<Attribute, String>();

    /**
     * Creates a new instance of the given {@link Type}.
     * 
     * @param type
     *            The type of this instance.
     */
    public Instance(Type type) {
        this.type = type;
    }

    /**
     * Sets the value of the given {@link Attribute}.
     * 
     * @param attribute
     *            The attribute. Must be defined for the {@link Type} of this
     *            instance.
     * @param value
     *            The value.
     * @throws UndefinedAttributeException
     *             if the given attribute is not defined in the {@link Type} of
     *             this instance.
     */
    public void setValue(Attribute attribute, String value) {
        assertHasAttribute(attribute);
        valueMap.put(attribute, value);
    }

    /**
     * Returns the value of the given attribute.
     * 
     * @param attribute
     *            The attribute. Must be defined for the {@link Type} of this
     *            instance.
     * @return The value or {@code null} if no value defined for this attribute.
     * @throws UndefinedAttributeException
     *             if the given attribute is not defined in the {@link Type} of
     *             this instance.
     */
    public String getValue(Attribute attribute) {
        assertHasAttribute(attribute);
        return valueMap.get(attribute);
    }

    private void assertHasAttribute(Attribute attribute) {
        if (!type.hasAttribute(attribute)) {
            throw new UndefinedAttributeException(attribute);
        }
    }
}
