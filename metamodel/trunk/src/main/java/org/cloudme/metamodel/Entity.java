package org.cloudme.metamodel;

public interface Entity {
    String getName();

    void addProperty(String name, Type type);

    Instance newInstance();
}
