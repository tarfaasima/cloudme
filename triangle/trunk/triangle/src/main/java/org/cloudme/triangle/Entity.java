package org.cloudme.triangle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.cloudme.triangle.annotations.AnnotationHelper;
import org.cloudme.triangle.annotations.Label;
import org.cloudme.triangle.annotations.MainEntity;

public class Entity {
    private final Class<?> type;
    private final String name;
    private final String label;
    private final boolean isMainEntity;
    private final Collection<Attribute> attributes = new ArrayList<Attribute>();

    public Entity(Class<?> type) {
        this.type = type;

        name = type.getSimpleName();
        label = new AnnotationHelper<String>(type, Label.class, name).value();
        isMainEntity = new AnnotationHelper<Boolean>(type, MainEntity.class, false).value();
        for (final Field field : type.getDeclaredFields()) {
            attributes.add(new Attribute(this, field));
        }
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public boolean isMainEntity() {
        return isMainEntity;
    }

    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    public void validate(Object object) {
        for (final Attribute attribute : attributes) {
            attribute.validate(object);
        }
    }
}
