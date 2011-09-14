package org.cloudme.passwolk.servlet.json;

import java.io.PrintWriter;
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

    private final PrintWriter out;

    public JsonWriter(PrintWriter out) {
        this.out = out;
    }

    public void write(Iterable<?> iterable) {
        out.write("[");
        for (Object object : iterable) {
            write(object);
        }
        out.write("]");
        out.flush();
    }

	public void write(Object object) {
        out.write("{");
        try {
            @SuppressWarnings( "unchecked" )
            Map<String, Object> props = PropertyUtils.describe(object);
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
        }
        catch (Exception e) {
            // ignore
        }
        out.write("}");
    }

}
