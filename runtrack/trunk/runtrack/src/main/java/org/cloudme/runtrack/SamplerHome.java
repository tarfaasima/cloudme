package org.cloudme.runtrack;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.vaadin.touchkit.TouchLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class SamplerHome extends TouchLayout {
    
    public SamplerHome() {
        
        setCaption("Vaadin Sampler");
        
        User user = UserServiceFactory.getUserService().getCurrentUser();
        String nickname = user.getNickname();
		String userId = user.getUserId();
		addComponent(new Label("<p>This is the mobile version of the Vaadin " +
                "sampler. It showcases some of the Vaadin components " +
                "that are suited for use in mobile applications. </p>" +
                "<p>You can add the application to you home screen to make " +
                "the application feel more native. " + nickname + "</p>", Label.CONTENT_XHTML));
		
		Form form = new Form();
		form.addField("name", new TextField());
		addComponent(form);
    }
}
