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

import org.cloudme.triangle.annotation.Convert;
import org.cloudme.triangle.annotation.Label;
import org.cloudme.triangle.annotation.Required;
import org.cloudme.triangle.annotation.Validate;
import org.cloudme.triangle.convert.Converter;
import org.cloudme.triangle.validation.ValidationException;
import org.cloudme.triangle.validation.Validator;
import org.cloudme.util.AnnotationUtils;
import org.cloudme.util.ClassUtils;

/**
 * Provides metadata of a field based on annotations, and provides validation.
 * 
 * @author Moritz Petersen
 */
public class Attribute<E, A> {
    /**
     * The technical name of the {@link Attribute}.
     */
    private final String name;
    /**
     * The human readable label of the {@link Attribute}, based on the
     * annotation {@link Label}.
     */
    private final String label;
    /**
     * Flag if the {@link Attribute} is required or not, based on the annotation
     * {@link Required}.
     */
    private final boolean isRequired;
    /**
     * The {@link Validator} based on the annotations.
     */
    private final Validator<A> validator;
    /**
     * The {@link Converter} based on the annotations.
     */
    private final Converter<A> converter;
    /**
     * The {@link Field} corresponding to this {@link Attribute}.
     */
    private final Field field;

    /**
     * Reads the metadata from annotations of the field and initializes the
     * {@link Validator}s.
     * 
     * @param entity
     *            The entity corresponding to this {@link Attribute}.
     * @param field
     *            The field corresponding to this {@link Attribute}
     */
    @SuppressWarnings( "unchecked" )
    Attribute(Entity<E> entity, Field field) {
        this.field = field;
        field.setAccessible(true);

        name = field.getName();
        label = AnnotationUtils.value(field, Label.class, name);
        isRequired = AnnotationUtils.value(field, Required.class, false);

        validator = (Validator<A>) Validate.Factory.newInstance(field);

        converter = (Converter<A>) Convert.Factory.newInstance(field);
    }

    /**
     * The technical name of the {@link Attribute}.
     * 
     * @return The technical name of the {@link Attribute}.
     */
    public String getName() {
        return name;
    }

    /**
     * The human readable label of the {@link Attribute}, based on the
     * annotation {@link Label}.
     * 
     * @return The human readable label of the {@link Attribute}, based on the
     *         annotation {@link Label}.
     */
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Flag if the {@link Attribute} is required or not, based on the annotation
     * {@link Required}.
     * 
     * @return Flag if the {@link Attribute} is required or not, based on the
     *         annotation {@link Required}.
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * Performs validation of the attribute on the given {@link Object}. The
     * object must be of the same type as the corresponding {@link Entity} type.
     * Also considers the {@link #isRequired()} attribute.
     * 
     * @param object
     *            The attribute of the given object is validated.
     */
    void validate(E object) {
        final A value = getValue(object);
        if (isRequired) {
            if (value == null) {
                throw new ValidationException(this, value);
            }
        }
        if (validator != null && value != null) {
            validator.validate(value);
        }
    }

    /**
     * Reads the {@link Attribute}'s value from the given object using
     * reflection.
     * 
     * @param object
     *            The object containing the attribute.
     * @return the {@link Attribute}'s value.
     */
    @SuppressWarnings( "unchecked" )
    private A getValue(E object) {
        try {
            return (A) field.get(object);
        } catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Formats the {@link Attribute}'s value of the given object.
     * 
     * @param object
     *            The object containing the attribute.
     * @return the formatted value.
     */
    String format(E object) {
        return object == null ? "" : converter == null ? object.toString()
                : converter.format(getValue(object));
    }

    /**
     * Converts the given {@link String} into a raw value and sets it in the
     * given {@link Object}.
     * 
     * @param object
     *            The {@link Object} which will receive the converted value.
     * @param str
     *            The {@link String} which will be converted.
     */
    @SuppressWarnings( "unchecked" )
    void convert(E object, String str) {
        setValue(object, (A) (str == null ? null : converter == null
                ? str
                : converter.convert(str)));
    }

    /**
     * Sets the value into the object using reflection.
     * 
     * @param object
     *            The object receiving the value.
     * @param value
     *            The value that is set.
     */
    private void setValue(E object, A value) {
        try {
            field.set(object, ClassUtils.convert(field.getType(), value));
        } catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
