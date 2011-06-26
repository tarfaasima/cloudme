package org.cloudme.passwolk.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LabeledWidget extends VerticalPanel {

    public LabeledWidget(String label, Widget widget) {
        add(new Label(label));
        add(widget);
    }

}
