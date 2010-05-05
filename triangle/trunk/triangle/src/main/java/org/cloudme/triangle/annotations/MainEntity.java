package org.cloudme.triangle.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MainEntity {
    boolean value() default true;
}
