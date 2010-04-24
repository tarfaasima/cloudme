package org.cloudme.runtrack;

import org.cloudme.runtrack.layout.EntryLayout;

import com.vaadin.Application;
import com.vaadin.touchkit.TouchPanel;
import com.vaadin.ui.Window;

public class RuntrackApplication extends Application {

    private static final long serialVersionUID = 5474522369804563317L;

    @Override
    public void init() {
        final Window mainWindow = new Window("Runtrack");
        final TouchPanel panel = new TouchPanel();
        panel.navigateTo(new EntryLayout());
        mainWindow.setContent(panel);
        setMainWindow(mainWindow);
        setTheme("touch");
    }
}