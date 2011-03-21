package org.cloudme.loclist.stripes.extensions;

import javax.servlet.ServletContext;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.loclist.item.ItemModule;
import org.cloudme.loclist.location.LocationModule;
import org.cloudme.loclist.note.NoteModule;
import org.cloudme.loclist.user.UserProfileModule;
import org.cloudme.loclist.user.UserProfileService;
import org.cloudme.sugar.AbstractActionBeanResolutionInterceptor;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Module;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class LoclistInterceptor extends AbstractActionBeanResolutionInterceptor {
    private static final Module[] MODULES = { new ItemModule(), new NoteModule(), new LocationModule(),
            new UserProfileModule() };
    @Inject
    private UserProfileService userProfileService;

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            userProfileService.login(user);
            NamespaceManager.set(user.getUserId());
            ServletContext servletContext = context.getActionBeanContext().getServletContext();
            servletContext.setAttribute("user", user);
            servletContext.setAttribute("logout", userService.createLogoutURL("/"));
        }
        return super.intercept(context);
    }

    @Override
    protected Module[] createModules() {
        return MODULES;
    }
}
