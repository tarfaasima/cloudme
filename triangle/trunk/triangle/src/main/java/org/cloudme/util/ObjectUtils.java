package org.cloudme.util;


public class ObjectUtils {
    public static <T, K> T checkNullOr(K undefined, T obj) {
        return obj == null || undefined.equals(obj) ? null : obj;
    }
}
