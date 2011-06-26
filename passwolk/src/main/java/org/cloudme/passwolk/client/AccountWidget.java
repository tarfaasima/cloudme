package org.cloudme.passwolk.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccountWidget extends Composite {
    public AccountWidget() {
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(new LabeledWidget("Name", new TextBox()));
        initWidget(vPanel);
    }
}
