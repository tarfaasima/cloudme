package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.triangle.Attribute;
import org.cloudme.triangle.validation.Validator;

/**
 * Defines the min value for validation. This value will be passed to the
 * {@link Attribute}s {@link Validator#setMin(double)} method in order to
 * perform specific validation.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Min {
    /**
     * The value of the annotation.
     * 
     * @return The value of the annotation.
     */
    double value();
}
