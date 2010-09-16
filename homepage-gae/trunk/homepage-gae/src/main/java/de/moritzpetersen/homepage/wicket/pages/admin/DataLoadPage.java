package de.moritzpetersen.homepage.wicket.pages.admin;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

public class DataLoadPage extends WebPage {
    public DataLoadPage() {
        add(new Form("dataLoadForm") {
            @Override
            protected void onSubmit() {
            }
        });
    }
}
