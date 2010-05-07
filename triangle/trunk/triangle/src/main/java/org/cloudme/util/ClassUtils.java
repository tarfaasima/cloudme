package org.cloudme.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Utility methods when working with {@link Class}es.
 * 
 * @author Moritz Petersen
 */
public class ClassUtils {
    /**
     * Checks if the given type is {@link String} or {@link CharSequence}.
     * 
     * @param type
     *            The type that is checked.
     * @return True if the type is {@link String} or {@link CharSequence}.
     */
    public static boolean isString(Class<?> type) {
        return CharSequence.class.isAssignableFrom(type);
    }

    /**
     * Checks if the given type is a {@link Number} or a primitive numeric type.
     * 
     * @param type
     *            The type that is checked.
     * @return True if the type is a {@link Number} or a primitive numeric type.
     */
    public static boolean isNumber(Class<?> type) {
        return Number.class.isAssignableFrom(type) || byte.class == type
                || short.class == type
                || int.class == type
                || long.class == type
                || float.class == type
                || double.class == type;
    }

    /**
     * Performs a type conversion of the given value into the given type if both
     * parameters are numeric types. Non numeric types will not be converted and
     * returned unchanged.
     * 
     * @param type
     *            The target type.
     * @param value
     *            The value that will be converted.
     * @return The converted value if both parameters are numeric types,
     *         otherwise the input value unchanged.
     */
    public static Object convert(Class<?> type, Object value) {
        if (isNumber(type) && isNumber(value.getClass())) {
            final Number number = (Number) value;
            if (byte.class == type) {
                return number.byteValue();
            }
            if (short.class == type) {
                return number.shortValue();
            }
            if (int.class == type) {
                return number.intValue();
            }
            if (long.class == type) {
                return number.longValue();
            }
            if (float.class == type) {
                return number.floatValue();
            }
            if (double.class == type) {
                return number.doubleValue();
            }
            try {
                return ((Constructor<?>) type.getConstructor(value.getClass())).newInstance(value);
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
            catch (final InstantiationException e) {
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
        }
        return value;
    }
}
