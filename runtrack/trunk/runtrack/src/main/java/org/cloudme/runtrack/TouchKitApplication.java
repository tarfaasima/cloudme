package org.cloudme.runtrack;

import org.vaadin.touchkit.TouchPanel;

import com.vaadin.Application;
import com.vaadin.ui.Window;

public class TouchKitApplication extends Application {

    private static final long serialVersionUID = 5474522369804563317L;

    @Override
    public void init() {
        final Window mainWindow = new Window("Sampler");
        final TouchPanel panel = new TouchPanel();

        panel.navigateTo(new SamplerHome());
        mainWindow.setContent(panel);
        
        setMainWindow(mainWindow);
        setTheme("touch");
    }
}