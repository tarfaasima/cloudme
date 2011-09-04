package org.cloudme.mediacopy;

import java.io.PrintWriter;

public class SimpleHtmlElement extends HtmlElement {
    public SimpleHtmlElement(String name, PrintWriter out, String value) {
        super(name, out);
        out.println(value);
        out.close();
    }
}
