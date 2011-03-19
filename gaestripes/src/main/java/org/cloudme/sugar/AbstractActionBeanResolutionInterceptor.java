package org.cloudme.sugar;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class AbstractActionBeanResolutionInterceptor implements Interceptor {
    private final Injector injector;

    public AbstractActionBeanResolutionInterceptor() {
        injector = Guice.createInjector(createModules());
        injector.injectMembers(this);
    }

    protected abstract Module[] createModules();

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        Resolution resolution = context.proceed();
        ActionBean actionBean = context.getActionBean();
        injectMembers(actionBean);
        return resolution;
    }

    protected void injectMembers(Object obj) {
        injector.injectMembers(obj);
    }
}
