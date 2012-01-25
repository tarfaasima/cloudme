package org.cloudme.notepad.stripes.action.logout;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.sugar.AbstractActionBean;

import com.google.appengine.api.users.UserServiceFactory;

@UrlBinding( "/app/logout" )
public class LogoutActionBean extends AbstractActionBean {
    @DefaultHandler
    public Resolution logout() {
        return new RedirectResolution(UserServiceFactory.getUserService().createLogoutURL("/"));
    }
}
