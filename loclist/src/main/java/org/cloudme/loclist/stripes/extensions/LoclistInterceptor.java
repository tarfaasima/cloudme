package org.cloudme.loclist.stripes.extensions;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.gaestripes.AbstractActionBeanResolutionInterceptor;
import org.cloudme.loclist.item.ItemModule;
import org.cloudme.loclist.location.LocationModule;
import org.cloudme.loclist.note.NoteModule;
import org.cloudme.loclist.user.UserProfileModule;
import org.cloudme.loclist.user.UserProfileService;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.User;
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
        User user = UserServiceFactory.getUserService().getCurrentUser();
        if (user != null) {
            userProfileService.login(user);
            NamespaceManager.set(user.getUserId());
            context.getActionBeanContext().getServletContext().setAttribute("user", user);
        }
        return super.intercept(context);
    }

    @Override
    protected Module[] createModules() {
        return MODULES;
    }
}
