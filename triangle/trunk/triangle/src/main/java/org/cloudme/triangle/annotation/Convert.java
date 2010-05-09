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
package org.cloudme.triangle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.cloudme.triangle.Attribute;
import org.cloudme.triangle.annotation.Convert.Factory.NoConverter;
import org.cloudme.triangle.convert.Converter;
import org.cloudme.triangle.convert.ConverterFactory;
import org.cloudme.util.ObjectUtils;

/**
 * Defines conversion options of an {@link Attribute}. Use
 * {@link Factory#newInstance(Field)} to create new {@link Converter}s based on
 * the annotation configuration.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Convert {
    /**
     * Creates {@link Converter} instances with the given annotation
     * configuration.
     * 
     * @author Moritz Petersen
     */
    static class Factory {
        static abstract class NoConverter implements Converter<Object> {
        }

        /**
         * Creates a new instance. If the field doesn't have a {@link Convert}
         * annotation, or if the pattern is not provided, a default
         * {@link Converter} for the field's type is returned. If that default
         * converter doesn't exist, <code>null</code> is returned.
         * 
         * @param field
         *            The field's annotations are used to create a
         *            {@link Converter} instance.
         * @return The {@link Converter} instance or <code>null</code> if no
         *         instance could be determine.
         */
        public static Converter<?> newInstance(Field field) {
            Convert annotation = field.getAnnotation(Convert.class);
            String pattern = null;
            Class<? extends Converter<?>> type = null;
            if (annotation != null) {
                pattern = ObjectUtils.checkNullOr(Converter.NO_PATTERN,
                        annotation.pattern());
                type = ObjectUtils.checkNullOr(NoConverter.class, annotation
                        .type());
            }
            Class<?> fieldType = field.getType();
            return ConverterFactory.newInstance(type, fieldType, pattern);
        }
    }

    /**
     * The pattern used for conversion.
     * 
     * @return The pattern used for conversion.
     */
    String pattern() default Converter.NO_PATTERN;

    /**
     * The custom converter type.
     * 
     * @return The custom converter type.
     */
    Class<? extends Converter<?>> type() default NoConverter.class;
}
