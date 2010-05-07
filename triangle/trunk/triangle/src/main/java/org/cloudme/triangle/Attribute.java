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
import java.util.ArrayList;
import java.util.Collection;

import org.cloudme.triangle.annotation.AnnotationHelper;
import org.cloudme.triangle.annotation.Label;
import org.cloudme.triangle.annotation.Mask;
import org.cloudme.triangle.annotation.Max;
import org.cloudme.triangle.annotation.Min;
import org.cloudme.triangle.annotation.Pattern;
import org.cloudme.triangle.annotation.Required;
import org.cloudme.triangle.annotation.ValidatorType;
import org.cloudme.triangle.convert.Converter;
import org.cloudme.triangle.convert.ConverterFactory;
import org.cloudme.triangle.format.Formatter;
import org.cloudme.triangle.format.FormatterFactory;
import org.cloudme.triangle.validation.RequiredValidator;
import org.cloudme.triangle.validation.Validator;
import org.cloudme.triangle.validation.ValidatorFactory;
import org.cloudme.util.ClassUtils;

/**
 * Provides metadata of a field based on annotations, and provides validation.
 * 
 * @author Moritz Petersen
 */
public class Attribute {
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
     * The {@link Validator}s based on the annotations.
     */
    private final Collection<Validator<?>> validators = new ArrayList<Validator<?>>();
    /**
     * The {@link Formatter} based on the annotations.
     */
    @SuppressWarnings( "unchecked" )
    private Formatter formatter;
    /**
     * The {@link Converter} based on the annotations.
     */
    @SuppressWarnings( "unchecked" )
    private Converter converter;
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
    Attribute(Entity entity, Field field) {
        this.field = field;
        field.setAccessible(true);

        name = field.getName();
        label = new AnnotationHelper<String>(field, Label.class, name).value();
        isRequired = new AnnotationHelper<Boolean>(field, Required.class, false).value();
        if (isRequired) {
            validators.add(new RequiredValidator());
        }

        final Class<? extends Validator<?>> validatorType = new AnnotationHelper<Class<? extends Validator<?>>>(field,
                ValidatorType.class).value();
        final Validator<?> validator = ValidatorFactory.newInstanceFor(validatorType, field.getType());
        if (validator != null) {
            validator.setMask(new AnnotationHelper<String>(field, Mask.class).value());
            validator.setMax(new AnnotationHelper<Double>(field, Max.class).value());
            validator.setMin(new AnnotationHelper<Double>(field, Min.class).value());
            validators.add(validator);
        }

        final String pattern = new AnnotationHelper<String>(field, Pattern.class).value();
        if (pattern != null) {
            formatter = FormatterFactory.newInstanceFor(field.getType());
            if (formatter != null) {
                formatter.setPattern(pattern);
            }

            converter = ConverterFactory.newInstanceFor(field.getType());
            if (converter != null) {
                converter.setPattern(pattern);
            }
        }
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
     * 
     * @param object
     *            The attribute of the given object is validated.
     */
    @SuppressWarnings( "unchecked" )
    void validate(Object object) {
        final Object value = getValue(object);
        if (isRequired || value != null) {
            for (final Validator validator : validators) {
                validator.validate(value);
            }
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
    private Object getValue(Object object) {
        try {
            return field.get(object);
        }
        catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IllegalAccessException e) {
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
    @SuppressWarnings( "unchecked" )
    String format(Object object) {
        return object == null ? "" : formatter == null ? object.toString() : formatter.format(getValue(object));
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
    void convert(Object object, String str) {
        setValue(object, str == null ? null : converter == null ? str : converter.convert(str));
    }

    /**
     * Sets the value into the object using reflection.
     * 
     * @param object
     *            The object receiving the value.
     * @param value
     *            The value that is set.
     */
    private void setValue(Object object, Object value) {
        try {
            field.set(object, ClassUtils.convert(field.getType(), value));
        }
        catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
