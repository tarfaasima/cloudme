package com.vaadin.demo.touch;

import org.vaadin.touchkit.TouchLayout;

import com.vaadin.ui.Label;

public class SamplerHome extends TouchLayout {
    
    public SamplerHome() {
        
        setCaption("Vaadin Sampler");
        
        addComponent(new Label("<p>This is the mobile version of the Vaadin " +
                "sampler. It showcases some of the Vaadin components " +
                "that are suited for use in mobile applications. </p>" +
                "<p>You can add the application to you home screen to make " +
                "the application feel more native. </p>", Label.CONTENT_XHTML));
    }	
}
