package org.cloudme.triangle.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

public class ValidationHelper {
    public static void addTo(Collection<Validator> validators,
            Field field,
            Class<? extends Annotation> annotationClass) {
        final Annotation annotation = field.getAnnotation(annotationClass);
        if (annotation != null) {
            validators.add(ValidatorFactory.getInstance(annotationClass, field.getType()));
        }
    }
}
