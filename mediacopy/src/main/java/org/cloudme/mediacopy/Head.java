package org.cloudme.mediacopy;

import java.io.PrintWriter;

public class Head extends HtmlElement {
    public Head(PrintWriter out) {
        super("head", out);
    }

    public void setTitle(String title) {
        new SimpleHtmlElement("title", out, title);
    }
}
