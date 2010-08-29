package de.moritzpetersen.homepage.util;

public class ObjectUtil {
    private static String toString(Object[] objs) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Object obj : objs) {
            sb.append(toString(obj));
        }
        sb.append("}");
        return sb.toString();
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj.getClass().isArray()) {
            return toString((Object[]) obj);
        }
        return obj.toString();
    }
}
