package org.cloudme.mediacopy;

import java.io.File;
import java.io.PrintWriter;

public class FileIndex {

    private final File destDir;

    public FileIndex(File destDir) {
        this.destDir = destDir;
    }

    public void writeHtml(PrintWriter out) {
        HtmlDocument htmlDocument = new HtmlDocument(out);
        htmlDocument.getHead();
    }

}
