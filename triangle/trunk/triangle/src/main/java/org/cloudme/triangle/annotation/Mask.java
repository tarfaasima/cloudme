package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.triangle.Attribute;
import org.cloudme.triangle.validation.Validator;

/**
 * Defines the mask for validation. This value will be passed to the
 * {@link Attribute}s {@link Validator#setMask(String)} method in order to
 * perform specific validation.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Mask {
    /**
     * The value of the annotation.
     * 
     * @return The value of the annotation.
     */
    String value();
}
