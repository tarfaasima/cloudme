package org.cloudme.triangle.convert;

import org.cloudme.triangle.Attribute;
import org.cloudme.util.ClassUtils;

/**
 * Creates instances of the {@link Converter} for the given {@link Attribute}
 * type.
 * 
 * @author Moritz Petersen
 */
public class ConverterFactory {
    /**
     * Creates a new {@link Converter} instance for the given type.
     * 
     * @param type
     *            The type for which the {@link Converter} will be created.
     * @return The {@link Converter} instance or null if no {@link Converter}
     *         exists for the given type.
     */
    public static Converter<?> newInstanceFor(Class<?> type) {
        if (ClassUtils.isNumber(type)) {
            return new NumberConverter();
        }
        return null;
    }

}
