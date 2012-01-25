package org.cloudme.notepad.stripes.extensions;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.sugar.AbstractActionBeanResolutionInterceptor;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Module;

@Intercepts( LifecycleStage.ActionBeanResolution )
public class ActionBeanResolutionInterceptor extends AbstractActionBeanResolutionInterceptor {
    @Override
    protected Module[] createModules() {
		return GuiceModules.MODULES;
    }

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        NamespaceManager.set(UserServiceFactory.getUserService().getCurrentUser().getUserId());
        return super.intercept(context);
    }
}
