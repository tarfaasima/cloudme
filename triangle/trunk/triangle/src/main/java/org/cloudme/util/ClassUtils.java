package org.cloudme.util;

public class ClassUtils {
    public static boolean isString(Class<?> type) {
        return CharSequence.class.isAssignableFrom(type);
    }

    public static boolean isNumber(Class<?> type) {
        return Number.class.isAssignableFrom(type) || byte.class == type
                || short.class == type
                || int.class == type
                || long.class == type
                || float.class == type
                || double.class == type;
    }
}
