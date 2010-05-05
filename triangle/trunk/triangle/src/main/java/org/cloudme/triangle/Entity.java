package org.cloudme.triangle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.cloudme.triangle.annotation.AnnotationHelper;
import org.cloudme.triangle.annotation.Label;
import org.cloudme.triangle.annotation.MainEntity;

/**
 * The metadata of an annotated class, including validation.
 * 
 * @author Moritz Petersen
 */
public class Entity {
    /**
     * The type of the {@link Entity} class.
     */
    private final Class<?> type;
    /**
     * The technical name of the {@link Entity}.
     */
    private final String name;
    /**
     * The human readable name of the {@link Entity}, based on the {@link Label}
     * annotation.
     */
    private final String label;
    /**
     * Flag if the {@link Entity} is the main {@link Entity}, based on the
     * {@link MainEntity} annotation.
     */
    private final boolean isMainEntity;
    /**
     * The {@link Attribute}s of this {@link Entity}.
     */
    private final Collection<Attribute> attributes = new ArrayList<Attribute>();

    /**
     * Reads the metadata of the given type based on annotations.
     * 
     * @param type
     *            The type of this {@link Entity}.
     */
    Entity(Class<?> type) {
        this.type = type;

        name = type.getSimpleName();
        label = new AnnotationHelper<String>(type, Label.class, name).value();
        isMainEntity = new AnnotationHelper<Boolean>(type, MainEntity.class, false).value();
        for (final Field field : type.getDeclaredFields()) {
            attributes.add(new Attribute(this, field));
        }
    }

    /**
     * The technical name of the {@link Entity}.
     * 
     * @return The technical name of the {@link Entity}.
     */
    public String getName() {
        return name;
    }

    /**
     * The type of the {@link Entity} class.
     * 
     * @return The type of the {@link Entity} class.
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * The human readable name of the {@link Entity}, based on the {@link Label}
     * annotation.
     * 
     * @return The human readable name of the {@link Entity}, based on the
     *         {@link Label} annotation.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Flag if the {@link Entity} is the main {@link Entity}, based on the
     * {@link MainEntity} annotation.
     * 
     * @return Flag if the {@link Entity} is the main {@link Entity}, based on
     *         the {@link MainEntity} annotation.
     */
    public boolean isMainEntity() {
        return isMainEntity;
    }

    /**
     * The {@link Attribute}s of this {@link Entity}.
     * 
     * @return The {@link Attribute}s of this {@link Entity}.
     */
    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Validates the given object based on annotations. The type of the object
     * must be the same as this {@link Entity}.
     * 
     * @param object
     *            The object that is validated.
     */
    public void validate(Object object) {
        for (final Attribute attribute : attributes) {
            attribute.validate(object);
        }
    }
}
