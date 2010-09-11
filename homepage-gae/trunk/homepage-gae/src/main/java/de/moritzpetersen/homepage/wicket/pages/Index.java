package de.moritzpetersen.homepage.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Index extends WebPage {
    public Index() {
        add(new Label("message", "Hello World!"));
    }
}
