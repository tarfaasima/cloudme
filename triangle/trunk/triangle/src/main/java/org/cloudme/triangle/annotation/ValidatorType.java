package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.triangle.Attribute;
import org.cloudme.triangle.validation.Validator;

/**
 * Defines the custom {@link Validator} type for an {@link Attribute}.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface ValidatorType {
    /**
     * The value of the annotation.
     * 
     * @return The value of the annotation.
     */
    Class<? extends Validator> value();
}
