package org.cloudme.metamodel;

import java.util.Collection;

import org.cloudme.metamodel.util.ValidationError;

public interface Instance {
    void setValue(String name, String value);

    Collection<ValidationError> validate();
}
