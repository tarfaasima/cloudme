package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.triangle.Attribute;

/**
 * Defines if the {@link Attribute} is required.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Required {
    /**
     * The value of the annotation.
     * 
     * @return The value of the annotation.
     */
    boolean value() default true;
}
