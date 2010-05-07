package org.cloudme.triangle.format;

import org.cloudme.triangle.Attribute;
import org.cloudme.util.ClassUtils;

/**
 * Creates instances of the {@link Formatter} for the given {@link Attribute}
 * type.
 * 
 * @author Moritz Petersen
 */
public class FormatterFactory {
    /**
     * Creates a new {@link Formatter} instance for the given type.
     * 
     * @param type
     *            The type for which the {@link Formatter} will be created.
     * @return The {@link Formatter} instance or null if no {@link Formatter}
     *         exists for the given type.
     */
    public static Formatter<?> newInstanceFor(Class<?> type) {
        if (ClassUtils.isNumber(type)) {
            return new NumberFormatter();
        }
        return null;
    }
}
