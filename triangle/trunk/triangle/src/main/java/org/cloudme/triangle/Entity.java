// Copyright 2010 Moritz Petersen
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.cloudme.triangle;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cloudme.triangle.annotation.Display;
import org.cloudme.triangle.annotation.MainEntity;
import org.cloudme.util.AnnotationUtils;

/**
 * The metadata of an annotated class, including validation.
 * 
 * @author Moritz Petersen
 */
public class Entity<E> {
    /**
     * The type of the {@link Entity} class.
     */
    private final Class<?> type;
    /**
     * The technical name of the {@link Entity}.
     */
    private final String name;
    /**
     * The human readable name of the {@link Entity}, based on the
     * {@link Display} annotation.
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
    private final Map<String, Attribute<E, ?>> attributes = new HashMap<String, Attribute<E, ?>>();

    /**
     * Reads the metadata of the given type based on annotations.
     * 
     * @param type
     *            The type of this {@link Entity}.
     */
    @SuppressWarnings( "unchecked" )
    Entity(Class<E> type) {
        this.type = type;

        name = type.getSimpleName();
        Display display = type.getAnnotation(Display.class);
        if (display != null) {
            label = display.label();
        }
        else {
            label = name;
        }
        isMainEntity = AnnotationUtils.value(type, MainEntity.class, false);
        for (final Field field : type.getDeclaredFields()) {
            final Attribute<E, ?> attribute = new Attribute(field);
            attributes.put(attribute.getName(), attribute);
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
     * The human readable name of the {@link Entity}, based on the
     * {@link Display} annotation.
     * 
     * @return The human readable name of the {@link Entity}, based on the
     *         {@link Display} annotation.
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
    public Collection<Attribute<E, ?>> getAttributes() {
        return attributes.values();
    }

    /**
     * The {@link Attribute} with the given technical name.
     * 
     * @param name
     *            The name of the {@link Attribute}.
     * @return The {@link Attribute} with the given technical name.
     */
    public Attribute<E, ?> getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Validates the given object based on annotations. The type of the object
     * must be the same as this {@link Entity}.
     * 
     * @param object
     *            The object that is validated.
     */
    public void validate(E object) {
        for (final Attribute<E, ?> attribute : attributes.values()) {
            attribute.validate(object);
        }
    }
}
