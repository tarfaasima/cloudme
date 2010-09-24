package org.cloudme.stripestools;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public abstract class GuiceInterceptor implements Interceptor {
    private final Injector injector;

    public GuiceInterceptor() {
        injector = Guice.createInjector(createModule());
    }

    protected abstract Module createModule();

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        Resolution resolution = context.proceed();
        injector.injectMembers(context.getActionBean());
        return resolution;
    }
}
