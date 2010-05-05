package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.triangle.Entity;

/**
 * Defines if the {@link Entity} is the main {@link Entity}.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface MainEntity {
    /**
     * The value of the annotation.
     * 
     * @return The value of the annotation.
     */
    boolean value() default true;
}
