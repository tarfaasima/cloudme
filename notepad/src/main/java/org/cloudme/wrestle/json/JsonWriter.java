package org.cloudme.wrestle.json;

import java.io.PrintWriter;

public class JsonWriter {

    private final PrintWriter out;

    public JsonWriter(PrintWriter out) {
        this.out = out;
    }

    public void write(Object obj) {
        if (obj instanceof Iterable<?>) {
            Iterable<?> it = (Iterable<?>) obj;
            out.write('[');
            for (Object el : it) {
                write(el);
            }
            out.write(']');
        }
        else if (obj.getClass().isArray()) {
            out.write('[');
            for (Object el : ((Object[]) obj)) {
                write(el);
            }
            out.write(']');
        }
    }

}
