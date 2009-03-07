package org.cloudme.metamodel;

import java.util.ArrayList;
import java.util.Collection;

public class Type {
    private final String name;
    private final Collection<Attribute> attributes = new ArrayList<Attribute>();

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public boolean hasAttribute(Attribute attribute) {
        return attributes.contains(attribute);
    }
}
