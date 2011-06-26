package org.cloudme.passwolk.gwt.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AccountWidget extends Composite {
    private final VerticalPanel vPanel = new VerticalPanel();
    private final TextBox nameTextBox = new TextBox();
    private final TextBox urlTextBox = new TextBox();
    private final TextBox loginTextBox = new TextBox();
    private final TextBox emailTextBox = new TextBox();
    private final TextBox passwordTextBox = new TextBox();

    public AccountWidget() {
        add("Name", nameTextBox);
        add("URL", urlTextBox);
        add("Login", loginTextBox);
        add("Email", emailTextBox);
        // add("Password", passwordTextBox);
        DisclosurePanel passwordPanel = new DisclosurePanel("Password");
        passwordPanel.add(passwordTextBox);
        vPanel.add(passwordPanel);

        initWidget(vPanel);
    }

    private void add(String text, Widget widget) {
        vPanel.add(new Label(text));
        vPanel.add(widget);
    }
}
