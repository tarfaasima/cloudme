package org.cloudme.passwolk.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


public class PasswolkEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        RootPanel.get().add(new AccountWidget());
    }
}
