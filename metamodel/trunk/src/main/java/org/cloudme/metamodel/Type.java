package org.cloudme.metamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link Type} of {@link Property} definitions.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 */
public enum Type {
    /**
     * Representing a text.
     */
    STRING("xs:string"),
    /**
     * Representing a decimal number.
     */
    DECIMAL("xs:decimal");

    private final String xsdDataType;
    private static final Map<String, Type> lookup = new HashMap<String, Type>();

    static {
        for (Type t : Type.values()) {
            lookup.put(t.xsdDataType, t);
        }
    }

    Type(String xsdDataType) {
        this.xsdDataType = xsdDataType;
    }

    /**
     * Returns the corresponding datatype of the XML Schema.
     * 
     * @return the corresponding datatype of the XML Schema.
     */
    public String getXsdDataType() {
        return xsdDataType;
    }

    /**
     * Looks up the given XSD datatype and retrieves the corresponding
     * {@link Type}.
     * 
     * @param xsdDataType
     *            The datatype of the XML Schema.
     * 
     * @return The corresponding {@link Type}.
     */
    public static Type get(String xsdDataType) {
        return lookup.get(xsdDataType);
    }
}
