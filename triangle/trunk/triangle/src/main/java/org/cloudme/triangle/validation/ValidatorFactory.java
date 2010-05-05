package org.cloudme.triangle.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.cloudme.triangle.annotations.Max;

public class ValidatorFactory {
    private static final Map<Class<? extends Annotation>, Map<Class<?>, Validator>> INSTANCES = new HashMap<Class<? extends Annotation>, Map<Class<?>, Validator>>();

    static {
        final Map<Class<?>, Validator> maxValidators = new HashMap<Class<?>, Validator>();
        maxValidators.put(String.class, new Validator() {
            public void validate(Object object) {
            }
        });
        INSTANCES.put(Max.class, maxValidators);
    }

    public static Validator getInstance(Class<? extends Annotation> annotationClass, Class<?> type) {
        return INSTANCES.get(annotationClass).get(type);
    }
}
