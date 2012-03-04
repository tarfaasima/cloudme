package org.cloudme.notepad.export.excel;

import java.io.IOException;
import java.io.Writer;

import org.cloudme.notepad.jsp.tags.Formatter;

public class ExcelFormatter extends Formatter {

    private boolean isList;

    @Override
    protected void writeChar(Writer out, char ch) throws IOException {
        out.write(ch);
    }

    @Override
    protected void writeNewLine(Writer out) throws IOException {
        out.write("\n");
    }

    @Override
    protected void writeParagraph(Writer out) throws IOException {
        out.write("\n\n");
    }

    @Override
    protected void writeListStart(Writer out) throws IOException {
        // do nothing
    }

    @Override
    protected void writeListEnd(Writer out) throws IOException {
        out.write("\n");
        isList = false;
    }

    @Override
    protected void writeListItem(Writer out) throws IOException {
        if (isList == true) {
            out.write("\n");
        }
        out.write("\u2022 ");
        isList = true;
    }

    @Override
    protected void writeRightArrow(Writer out) throws IOException {
        out.write("\u2192");
    }

    @Override
    protected void writeLeftArrow(Writer out) throws IOException {
        out.write("\u2190");
    }

    @Override
    protected void writeUrl(Writer out, String url) throws IOException {
        out.write(url);
    }

    @Override
    protected void writeEmphasized(Writer out, String str) throws IOException {
        out.write(str);
    }

}
