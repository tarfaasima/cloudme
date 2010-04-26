package org.cloudme.runtrack.view;

import java.util.Arrays;

import org.cloudme.runtrack.model.Route;

import com.vaadin.data.util.BeanItem;
import com.vaadin.touchkit.TouchLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class NewRouteView extends TouchLayout {
    public NewRouteView() {
        Route route = new Route();

        final Form form = new Form();
        MapFormFieldFactory fieldFactory = new MapFormFieldFactory();

        TextField nameField = new TextField("Name");
        nameField.setNullRepresentation("");
        fieldFactory.addField("name", nameField);

        fieldFactory.addField("type", new NativeSelect("Type", Arrays.asList("Running")));
        fieldFactory.addField("distance", new TextField("Distance (km)"));

        TextField locationField = new TextField("Location");
        locationField.setNullRepresentation("");
        fieldFactory.addField("location", locationField);

        form.setFormFieldFactory(fieldFactory);
        form.setItemDataSource(new BeanItem<Route>(route), fieldFactory.getFieldOrder());
        addComponent(form);

        VerticalLayout buttonLayout = new VerticalLayout();
        NativeButton saveButton = new NativeButton("Save", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                form.commit();
            }
        });
        buttonLayout.addComponent(saveButton);
        buttonLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);
        addComponent(buttonLayout);
    }
}
