package org.cloudme.mediacopy;

import java.io.PrintWriter;

public class HtmlDocument extends HtmlElement {
    public HtmlDocument(PrintWriter out) {
        super("html", out);
    }

    public Head getHead() {
        return new Head(out);
    }

    public Body getBody() {
        return new Body(out);
    }
}
