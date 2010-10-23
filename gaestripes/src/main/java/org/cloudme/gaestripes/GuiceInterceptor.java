package org.cloudme.gaestripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class GuiceInterceptor implements Interceptor {
    private final Injector injector;

    public GuiceInterceptor() {
        injector = Guice.createInjector(createModule());
    }

    protected abstract Module createModule();

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        Resolution resolution = context.proceed();
        ActionBean actionBean = context.getActionBean();
        injector.injectMembers(actionBean);
        return resolution;
    }
}
