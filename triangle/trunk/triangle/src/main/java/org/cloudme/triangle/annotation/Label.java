package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.triangle.Attribute;
import org.cloudme.triangle.Entity;

/**
 * Defines the human readable label of the {@link Entity} or {@link Attribute}.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.TYPE } )
public @interface Label {
    /**
     * The value of the annotation.
     * 
     * @return The value of the annotation.
     */
    String value();
}
