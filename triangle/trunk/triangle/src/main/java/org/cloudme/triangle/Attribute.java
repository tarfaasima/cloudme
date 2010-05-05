package org.cloudme.triangle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.cloudme.triangle.annotation.AnnotationHelper;
import org.cloudme.triangle.annotation.Label;
import org.cloudme.triangle.annotation.Mask;
import org.cloudme.triangle.annotation.Max;
import org.cloudme.triangle.annotation.Min;
import org.cloudme.triangle.annotation.Required;
import org.cloudme.triangle.annotation.ValidatorType;
import org.cloudme.triangle.validation.NumberValidator;
import org.cloudme.triangle.validation.RequiredValidator;
import org.cloudme.triangle.validation.StringValidator;
import org.cloudme.triangle.validation.Validator;

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
    private final Collection<Validator> validators = new ArrayList<Validator>();
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

        Validator validator = null;
        try {
            final Class<? extends Validator> validatorType = new AnnotationHelper<Class<? extends Validator>>(field,
                    ValidatorType.class).value();
            if (validatorType != null) {
                validator = validatorType.newInstance();
            }
        }
        catch (final InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (validator == null) {
            if (String.class.isAssignableFrom(field.getType())) {
                validator = new StringValidator();
            }
            else if (Number.class.isAssignableFrom(field.getType())) {
                validator = new NumberValidator();
            }
        }
        if (validator != null) {
            final Max max = field.getAnnotation(Max.class);
            if (max != null) {
                validator.setMax(max.value());
            }
            final Min min = field.getAnnotation(Min.class);
            if (min != null) {
                validator.setMin(min.value());
            }
            final Mask mask = field.getAnnotation(Mask.class);
            if (mask != null) {
                validator.setMask(mask.value());
            }
            validators.add(validator);
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
    void validate(Object object) {
        try {
            final Object value = field.get(object);
            for (final Validator validator : validators) {
                validator.validate(value);
            }
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
