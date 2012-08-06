package org.cloudme.notepad.rest;

import org.cloudme.wrestle.ActionHandler;
import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.UrlMapping;

import com.google.appengine.api.users.UserServiceFactory;

@UrlMapping( "user" )
public class UserHandler implements ActionHandler {
    @Get
    public String name() {
        return UserServiceFactory.getUserService().getCurrentUser().getEmail();
    }
}
