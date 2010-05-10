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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

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
        return Number.class.isAssignableFrom(type)
                || byte.class == type
                || short.class == type
                || int.class == type
                || long.class == type
                || float.class == type
                || double.class == type;
    }

    /**
     * Checks if the given type is a {@link Date}.
     * 
     * @param type
     *            The type that is checked.
     * @return True if the type is a {@link Date}.
     */
    public static boolean isDate(Class<?> type) {
        return Date.class.isAssignableFrom(type);
    }

    /**
     * Checks if the given type is a {@link Boolean} or primitive boolean.
     * 
     * @param type
     *            The type that is checked.
     * @return True if the type is a {@link Boolean} or primitive boolean.
     */
    public static boolean isBoolean(Class<?> type) {
        return Boolean.class.isAssignableFrom(type) || boolean.class == type;
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
        if (value == null) {
            return null;
        }
        if (isString(type)) {
            if (value instanceof String) {
                return value;
            }
            return value.toString();
        }
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
                return ((Constructor<?>) type.getConstructor(value.getClass()))
                        .newInstance(value);
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

    public static <T> T instantiate(Class<T> type) {
        try {
            return type.newInstance();
        }
        catch (final InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
