package org.cloudme.notepad.jsp.tags;

import java.io.StringWriter;
import java.io.Writer;

public abstract class Formatter {
    public String format(String str) {
        Writer out = new StringWriter((int) (str.length() * 1.5));
        for (int i = 0, length = str.length(); i < length; i++) {
            format(out, str.charAt(i));
        }
        return str;
    }

    protected abstract void format(Writer out, char charAt);
}
