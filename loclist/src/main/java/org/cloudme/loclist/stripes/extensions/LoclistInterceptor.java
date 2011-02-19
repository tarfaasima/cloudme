package org.cloudme.loclist.stripes.extensions;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.gaestripes.AbstractActionBeanResolutionInterceptor;
import org.cloudme.loclist.guice.LoclistModule;
import org.cloudme.loclist.log.UserProfileService;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Module;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class LoclistInterceptor extends AbstractActionBeanResolutionInterceptor {
    @Inject
    private UserProfileService userProfileService;

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        User user = UserServiceFactory.getUserService().getCurrentUser();
        if (user != null) {
            userProfileService.log(user);
            NamespaceManager.set(user.getUserId());
            context.getActionBeanContext().getServletContext().setAttribute("user", user);
        }
        return super.intercept(context);
    }

    @Override
    protected Module createModule() {
        return new LoclistModule();
    }
}
