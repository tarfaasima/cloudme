package org.cloudme.metamodel;

import org.apache.commons.lang.ObjectUtils;

public class Attribute {
    private final String name;

    public Attribute(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Attribute) {
            return ObjectUtils.equals(name, ((Attribute) obj).name); 
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
