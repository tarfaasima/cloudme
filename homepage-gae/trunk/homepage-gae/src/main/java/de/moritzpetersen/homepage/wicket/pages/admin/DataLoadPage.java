package de.moritzpetersen.homepage.wicket.pages.admin;

import java.io.ByteArrayInputStream;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dataload.DataLoader;

public class DataLoadPage extends WebPage {
    @Inject
    private DataLoader dataLoader;

    @SuppressWarnings( "serial" )
    public DataLoadPage() {
        Form<String> form = new Form<String>("dataload");
        final TextArea<String> data = new TextArea<String>("data");
        form.add(data);
        form.add(new Button("submit") {
            @Override
            public void onSubmit() {
                byte[] bytes = data.getModelObject().getBytes();
                dataLoader.load(new ByteArrayInputStream(bytes));
            }
        });
        add(form);
    }
}
