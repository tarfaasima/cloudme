package de.moritzpetersen.homepage.wicket.pages.admin;

import java.io.ByteArrayInputStream;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dataload.DataLoader;

public class DataLoadPage extends WebPage {
    private static final Logger LOG = LoggerFactory
            .getLogger(DataLoadPage.class);
    @Inject
    private DataLoader dataLoader;

    @SuppressWarnings( "serial" )
    public DataLoadPage() {
        LOG.info("dataLoader = {}", dataLoader.getClass().getName());
        Form<String> form = new Form<String>("dataload");
        final TextArea<String> data = new TextArea<String>("data",
                new Model<String>());
        form.add(data);
        form.add(new Button("submit") {
            @Override
            public void onSubmit() {
                LOG.info("data = {}", data);
                byte[] bytes = data.getModelObject().getBytes();
                LOG.info("bytes.length = {}", bytes.length);
                dataLoader.load(new ByteArrayInputStream(bytes));
                LOG.info("done.");
            }
        });
        add(form);
    }
}
