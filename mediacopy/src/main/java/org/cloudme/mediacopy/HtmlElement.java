package org.cloudme.mediacopy;

import java.io.PrintWriter;

public abstract class HtmlElement {
    private final String name;
    protected final PrintWriter out;

    public HtmlElement(String name, PrintWriter out) {
        this.name = name;
        this.out = out;
        out.println("<" + name + ">");
    }

    public void close() {
        out.println("</" + name + ">");
    }
}
