package org.cloudme.triangle.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationHelper<T> {
    private final T value;

    public AnnotationHelper(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        value = invokeValue(element, annotationClass);
    }

    public AnnotationHelper(AnnotatedElement element, Class<? extends Annotation> annotationClass, T defaultValue) {
        final T invokedValue = invokeValue(element, annotationClass);
        value = invokedValue == null ? defaultValue : invokedValue;
    }

    @SuppressWarnings("unchecked")
    private T invokeValue(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        try {
            final Annotation annotation = element.getAnnotation(annotationClass);
            if (annotation != null) {
                final Method valueMethod = annotationClass.getDeclaredMethod("value");
                return (T) valueMethod.invoke(annotation);
            }
        } catch (final SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public T value() {
        return value;
    }
}
