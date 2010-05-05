package org.cloudme.triangle.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class that helps working with annotations. It is used to get the
 * value of an annotated element at runtime, if it is not clear whether the
 * annotation is actually applied to the element. A default value can be used to
 * provide an alternative if the annotation is not applied.
 * 
 * @author Moritz Petersen
 * @param <T>
 *            The type of the value that is read from the annotation using this
 *            utility class.
 */
public class AnnotationHelper<T> {
    /**
     * The value of this annotation.
     */
    private final T value;

    /**
     * Creates a new instance without default value.
     * 
     * @param element
     *            The annotated element.
     * @param annotationClass
     *            The type of the annotation.
     */
    public AnnotationHelper(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        value = invokeValue(element, annotationClass);
    }

    /**
     * Creates a new instance with a default value if the annotation is not
     * applied.
     * 
     * @param element
     *            The annotated element
     * @param annotationClass
     *            The type of the annotation.
     * @param defaultValue
     *            The default value if the annotation is not applied.
     */
    public AnnotationHelper(AnnotatedElement element, Class<? extends Annotation> annotationClass, T defaultValue) {
        final T invokedValue = invokeValue(element, annotationClass);
        value = invokedValue == null ? defaultValue : invokedValue;
    }

    /**
     * Reads the value from the annotation using reflection.
     * 
     * @param element
     *            The annotated element.
     * @param annotationClass
     *            The type of the annotation.
     * @return The value of the annotation or null if the annotation is not
     *         applied.
     */
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

    /**
     * The value of the annotation.
     * 
     * @return The value of the annotation.
     */
    public T value() {
        return value;
    }
}
