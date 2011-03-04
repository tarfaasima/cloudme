package org.cloudme.taskflow.stripes.extensions;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.gaestripes.AbstractActionBeanResolutionInterceptor;
import org.cloudme.taskflow.domain.User;
import org.cloudme.taskflow.guice.TaskFlowModule;
import org.cloudme.taskflow.user.UserService;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Module;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class ActionBeanResolutionInterceptor extends AbstractActionBeanResolutionInterceptor {
    private static final Module[] MODULES = { new TaskFlowModule() };
    @Inject
    private UserService userService;

    @Override
    protected Module[] createModules() {
        return MODULES;
    }

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        HttpServletRequest request = context.getActionBeanContext().getRequest();
        if (request.getUserPrincipal() != null) {
            com.google.appengine.api.users.User currentUser = UserServiceFactory.getUserService().getCurrentUser();
            String email = currentUser.getEmail();
            String nickname = currentUser.getNickname();
            String userId = currentUser.getUserId();
            User user = userService.create(email, nickname, userId);
            request.setAttribute("user", user);
        }
        return super.intercept(context);
    }
}
