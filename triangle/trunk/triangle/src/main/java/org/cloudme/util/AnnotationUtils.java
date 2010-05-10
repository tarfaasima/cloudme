// Copyright 2010 Moritz Petersen
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.cloudme.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class that helps working with annotations.
 * 
 * @author Moritz Petersen
 */
public class AnnotationUtils {
    /**
     * Returns the value of the annotation if exists, without default value.
     * 
     * @param element
     *            The annotated element.
     * @param annotationClass
     *            The type of the annotation.
     */
    public static <T> T value(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        return invokeValue(element, annotationClass);
    }

    /**
     * Returns the value of the annotation if exists, with default value if the
     * annotation is not applied.
     * 
     * @param element
     *            The annotated element
     * @param annotationClass
     *            The type of the annotation.
     * @param defaultValue
     *            The default value if the annotation is not applied.
     */
    public static <T> T value(AnnotatedElement element, Class<? extends Annotation> annotationClass, T defaultValue) {
        final T value = invokeValue(element, annotationClass);
        return value == null ? defaultValue : value;
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
    @SuppressWarnings( "unchecked" )
    private static <T> T invokeValue(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        try {
            final Annotation annotation = element.getAnnotation(annotationClass);
            if (annotation != null) {
                final Method valueMethod = annotationClass.getDeclaredMethod("value");
                return (T) valueMethod.invoke(annotation);
            }
        }
        catch (final SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
