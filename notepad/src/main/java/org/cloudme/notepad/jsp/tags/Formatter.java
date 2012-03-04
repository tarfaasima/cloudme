package org.cloudme.notepad.jsp.tags;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import lombok.SneakyThrows;

public abstract class Formatter {

    private boolean prevLineEmpty;
    private boolean prevLineList;

    @SneakyThrows
    public String format(String str) {
        Writer out = new StringWriter((int) (str.length() * 1.5));
        String[] lines = str.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) {
                if (prevLineList) {
                    writeListEnd(out);
                }
                else if (!prevLineEmpty && !(lines.length > i + 1 && isList(lines[i + 1].trim()))) {
                    writeParagraph(out);
                }
                prevLineEmpty = true;
                prevLineList = false;
            }
            else {
                if (isList(trimmedLine)) {
                    if (!prevLineList) {
                        writeListStart(out);
                    }
                    writeListItem(out);
                    format(out, trimmedLine.substring(2));
                    prevLineList = true;
                }
                else {
                    if (i > 0 && !prevLineEmpty) {
                        writeNewLine(out);
                    }
                    format(out, line);
                }
                prevLineEmpty = false;
            }
        }
        if (prevLineList) {
            writeListEnd(out);
        }
        return out.toString();
    }

    private boolean isList(String line) {
        return line.startsWith("- ") || line.startsWith("* ");
    }

    protected void format(Writer out, String str) throws IOException {
        for (int i = 0, length = str.length(); i < length; i++) {
            if (isAtPos(str, i, "-->")) {
                writeRightArrow(out);
                i += 2;
            }
            else if (isAtPos(str, i, "<--")) {
                writeLeftArrow(out);
                i += 2;
            }
            else if (isAtPos(str, i, "http://")) {
                int end = str.indexOf(' ', i + 7);
                if (end == -1) {
                    end = length;
                }
                String url = str.substring(i, end);
                writeUrl(out, url);
                i = end - 1;
            }
            else if (isAtPos(str, i, "*")) {
                int end = str.indexOf('*', i + 1);
                if (end == -1) {
                    writeChar(out, '*');
                }
                else {
                    writeEmphasized(out, str.substring(i + 1, end));
                    i = end;
                }
            }
            else {
                writeChar(out, str.charAt(i));
            }
        }
    }

    private boolean isAtPos(String str, int start, String token) {
        if (str.length() < token.length() + start) {
            return false;
        }
        for (int i = 0, len = token.length(); i < len; i++) {
            if (str.charAt(start + i) != token.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    protected abstract void writeChar(Writer out, char charAt) throws IOException;

    protected abstract void writeNewLine(Writer out) throws IOException;

    protected abstract void writeParagraph(Writer out) throws IOException;

    protected abstract void writeListStart(Writer out) throws IOException;

    protected abstract void writeListEnd(Writer out) throws IOException;

    protected abstract void writeListItem(Writer out) throws IOException;

    protected abstract void writeRightArrow(Writer out) throws IOException;

    protected abstract void writeLeftArrow(Writer out) throws IOException;

    protected abstract void writeUrl(Writer out, String url) throws IOException;

    protected abstract void writeEmphasized(Writer out, String substring) throws IOException;
}
