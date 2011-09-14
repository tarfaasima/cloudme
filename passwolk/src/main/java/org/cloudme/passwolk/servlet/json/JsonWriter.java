package org.cloudme.passwolk.servlet.json;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Provides basic support to write JSON data from Java objects. Currently
 * supports {@link Iterable} and {@link Object}.
 * <p>
 * This class requires a {@link PrintWriter} to generate output.
 * 
 * @author Moritz Petersen
 * 
 */
public class JsonWriter {

    /**
     * The internal writer.
     */
    private final PrintWriter out;

    /**
     * Initializes the {@link JsonWriter} with the given {@link PrintWriter}.
     * 
     * @param out
     *            The {@link PrintWriter} that is used to write JSON data to.
     */
    public JsonWriter(PrintWriter out) {
        this.out = out;
    }

    /**
     * Writes the objects of the given {@link Iterable}.
     * 
     * @param iterable
     *            The input that is transformed to JSON.
     */
    public void write(Iterable<?> iterable) {
        out.write("[");
        for (Object object : iterable) {
            write(object);
        }
        out.write("]");
        out.flush();
    }

    /**
     * Writes a single {@link Object}.
     * 
     * @param object
     *            The object that ist transformed to JSON.
     */
    public void write(Object object) {
        Map<String, Object> props = describe(object);
        if (props != null) {
            out.write("{");
            boolean isFirst = true;
            for (Entry<String, Object> property : props.entrySet()) {
                String key = property.getKey();
                if (!"class".equals(key)) {
                    if (isFirst) {
                        isFirst = false;
                    }
                    else {
                        out.write(',');
                    }
                    out.write('"');
                    out.write(key);
                    out.write("\":");
                    Object value = property.getValue();
                    if (value == null || value instanceof Number || value instanceof Boolean) {
                        out.write(String.valueOf(value));
                    }
                    else if (value instanceof String) {
                        out.write('"');
                        out.write((String) value);
                        out.write('"');
                    }
                    else {
                        write(value);
                    }
                }
            }
            out.write("}");
        }
    }

    /**
     * Creates a map containing all property names and values of the object.
     * 
     * @param object
     *            The object, that must follow the Java Bean syntax (using
     *            accessors in getXXX() setXXX style()).
     * 
     * @return A map containing the properties.
     */
    @SuppressWarnings( "unchecked" )
    private Map<String, Object> describe(Object object) {
        try {
            return PropertyUtils.describe(object);
        }
        catch (IllegalAccessException e) {
            return null;
        }
        catch (InvocationTargetException e) {
            return null;
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

}
