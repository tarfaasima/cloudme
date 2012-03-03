package org.cloudme.notepad.jsp.tags;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

public class HtmlFormatter extends Formatter {
    private static final Map<Character, String> entityMap = new HashMap<Character, String>();

    static {
    }

    @SneakyThrows
    @Override
    protected void format(Writer out, char ch) {
        out.write(ch);
    }

}
