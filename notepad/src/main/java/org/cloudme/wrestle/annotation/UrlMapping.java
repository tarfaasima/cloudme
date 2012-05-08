package org.cloudme.wrestle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudme.wrestle.ActionHandler;

/**
 * Maps the {@link ActionHandler} to a URL. This annotation defines the middle
 * part of the URL
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface UrlMapping {
    String value();
}
