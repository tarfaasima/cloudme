package org.cloudme.metamodel;

public enum Type {
    STRING("xs:string"), DECIMAL("xs:decimal");

    private final String xsdDataType;

    Type(String xsdDataType) {
        this.xsdDataType = xsdDataType;
    }
    
    public String getXsdDataType() {
        return xsdDataType;
    }
}
