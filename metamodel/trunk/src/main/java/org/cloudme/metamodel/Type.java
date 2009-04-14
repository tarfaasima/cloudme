package org.cloudme.metamodel;

import java.util.HashMap;
import java.util.Map;

public enum Type {
    STRING("xs:string"), DECIMAL("xs:decimal");

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
    
    public String getXsdDataType() {
        return xsdDataType;
    }

    public static Type get(String xsdDataType) {
        return lookup.get(xsdDataType);
    }
}
