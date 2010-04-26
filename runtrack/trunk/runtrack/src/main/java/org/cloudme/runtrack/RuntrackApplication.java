package org.cloudme.runtrack;

import org.cloudme.runtrack.view.EntryView;

import com.vaadin.Application;
import com.vaadin.touchkit.TouchPanel;
import com.vaadin.ui.Window;

public class RuntrackApplication extends Application {

    private static final long serialVersionUID = 5474522369804563317L;

    @Override
    public void init() {
        final Window main = new Window("Runtrack");
        final TouchPanel panel = new TouchPanel();
        panel.navigateTo(new EntryView());
        panel.setSizeFull();
        main.setContent(panel);
        setMainWindow(main);
        setTheme("touch");
    }
}