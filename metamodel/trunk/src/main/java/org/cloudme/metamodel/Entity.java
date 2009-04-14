package org.cloudme.metamodel;

import java.util.Collection;

public interface Entity {
    String getName();

    void addProperty(String name, Type type, String label);

    Instance newInstance();

    Collection<Property> getProperties();
}
