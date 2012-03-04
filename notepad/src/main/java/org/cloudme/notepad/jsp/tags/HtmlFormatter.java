package org.cloudme.notepad.jsp.tags;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class HtmlFormatter extends Formatter {
    private static final Map<Character, String> entityMap = new HashMap<Character, String>();

    static {
        entityMap.put('"', "quot");
        entityMap.put('&', "amp");
        entityMap.put('<', "lt");
        entityMap.put('>', "gt");
    }

    @Override
    protected void writeChar(Writer out, char ch) throws IOException {
        String name = entityMap.get(ch);
        if (name != null) {
            writeEntity(out, name);
        }
        else if (ch > 127) {
            writeEntity(out, "#" + Integer.toString(ch));
        }
        else {
            out.write(ch);
        }
    }

    private void writeEntity(Writer out, String name) throws IOException {
        out.write("&");
        out.write(name);
        out.write(";");
    }

    @Override
    protected void writeNewLine(Writer out) throws IOException {
        out.write("<br>");
    }

    @Override
    protected void writeParagraph(Writer out) throws IOException {
        out.write("<p>");
    }

    @Override
    protected void writeListStart(Writer out) throws IOException {
        out.write("<ul>");
    }

    @Override
    protected void writeListEnd(Writer out) throws IOException {
        out.write("</ul>");
    }

    @Override
    protected void writeListItem(Writer out) throws IOException {
        out.write("<li>");
    }

    @Override
    protected void writeRightArrow(Writer out) throws IOException {
        out.write("&rarr;");
    }

    @Override
    protected void writeLeftArrow(Writer out) throws IOException {
        out.write("&larr;");
    }

    @Override
    protected void writeUrl(Writer out, String url) throws IOException {
        for (int i = 0, len = url.length(); i < len; i++) {
            writeChar(out, url.charAt(i));
        }
    }

    @Override
    protected void writeEmphasized(Writer out, String substring) throws IOException {
        out.write("<em>");
        format(out, substring);
        out.write("</em>");
    }
}
