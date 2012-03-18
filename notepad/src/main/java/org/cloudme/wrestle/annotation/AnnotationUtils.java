package org.cloudme.wrestle.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AnnotationUtils {
    public static <T extends Annotation> boolean hasAnnotation(AccessibleObject obj, Class<T> annotation) {
        return obj.getAnnotation(annotation) != null;
    }
}
