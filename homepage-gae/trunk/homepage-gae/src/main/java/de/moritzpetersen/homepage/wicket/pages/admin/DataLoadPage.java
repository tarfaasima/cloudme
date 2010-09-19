package de.moritzpetersen.homepage.wicket.pages.admin;

import java.io.ByteArrayInputStream;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dataload.DataLoader;
import de.moritzpetersen.homepage.dataload.InvalidDataException;

public class DataLoadPage extends WebPage {
    @Inject
    private DataLoader dataLoader;

    @SuppressWarnings( "serial" )
    public DataLoadPage() {
        final Form<String> form = new Form<String>("dataload");
        final FormComponent<String> data = new TextArea<String>("data", new Model<String>()).setRequired(true);
        form.add(data);
        form.add(new Button("submit") {
            @Override
            public void onSubmit() {
                byte[] bytes = data.getModelObject().getBytes();
                try {
                    dataLoader.load(new ByteArrayInputStream(bytes));
                    info(getString("data.Success"));
                }
                catch (InvalidDataException e) {
                    error(getString("data.Invalid"));
                }
            }
        });
        add(form);
        add(new FeedbackPanel("feedback"));
    }
}
