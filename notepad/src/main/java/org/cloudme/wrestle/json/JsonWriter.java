package org.cloudme.wrestle.json;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

public class JsonWriter {
    private interface Formatter<T> {
        void format(T obj);
    }

    private static final char[] CONTROL_CHARS = "\"\"\\\\//\bb\nn\rr\tt".toCharArray();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("Z");
    private final PrintWriter out;

    public JsonWriter(PrintWriter out) {
        this.out = out;
    }

    @SuppressWarnings( "unchecked" )
    public void write(Object obj) {
        if (obj == null) {
            out.write("null");
        }
        else if (obj instanceof Iterable<?>) {
            write((Iterable<Object>) obj, '[', ']', new Formatter<Object>() {
                @Override
                public void format(Object obj) {
                    write(obj);
                }
            });
        }
        else if (obj instanceof Map) {
            write(((Map<String, Object>) obj).entrySet(), '{', '}', new Formatter<Entry<String, Object>>() {
                @Override
                public void format(Entry<String, Object> entry) {
                    write(entry.getKey().toString());
                    out.write(':');
                    write(entry.getValue());
                }
            });
        }
        else if (obj.getClass().isArray()) {
            write(Arrays.asList((Object[]) obj));
        }
        else if (obj instanceof String) {
            out.write('"');
            for (char ch : ((String) obj).toCharArray()) {
                write(ch);
            }
            out.write('"');
        }
        else if (obj instanceof Date) {
            Date date = (Date) obj;
            write("/Date(" + date.getTime() + DATE_FORMAT.format(date) + ")/");
        }
        else if (obj instanceof Number) {
            out.write(obj.toString());
        }
        else if (obj instanceof Boolean) {
            out.write(obj.toString());
        }
        else {
            try {
                Map<?, ?> properties = PropertyUtils.describe(obj);
                properties.remove("class");
                write(properties);
            }
            catch (Exception e) {
                // ignore
            }
        }
    }

    private <T> void write(Iterable<T> it, char before, char after, Formatter<T> formatter) {
        boolean isFirst = true;
        for (T obj : it) {
            if (isFirst) {
                out.write(before);
                isFirst = false;
            }
            else {
                out.write(',');
            }
            formatter.format(obj);
        }
        if (!isFirst) {
            out.write(after);
        }
    }

    private void write(char ch) {
        for (int j = 0; j < CONTROL_CHARS.length; j += 2) {
            if (ch == CONTROL_CHARS[j]) {
                out.write('\\');
                out.write(CONTROL_CHARS[j + 1]);
                return;
            }
        }
        out.write(ch);
    }

}
