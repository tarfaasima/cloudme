package org.cloudme.passwolk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Passwolk implements EntryPoint {
    @Override
    public void onModuleLoad() {
        RootPanel root = RootPanel.get();
        root.add(new AccountWidget());
    }
}
