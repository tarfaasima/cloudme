package org.cloudme.gaestripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.googlecode.objectify.ObjectifyService;

public abstract class AbstractActionBeanResolutionInterceptor implements Interceptor {
    private Injector injector;

    public AbstractActionBeanResolutionInterceptor() {
        initObjectify();
        initGuice();
    }

    public final void initObjectify() {
        for (Class<?> clazz : objectifyEntityClasses()) {
            System.out.println("registering " + clazz);
            ObjectifyService.register(clazz);
        }
    }

    public final void initGuice() {
        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                for (Class<?> clazz : guiceModuleClasses()) {
                    bind(clazz);
                }
            }
        };
        injector = Guice.createInjector(module);
        injectMembers(this);
    }

    protected abstract Class<?>[] guiceModuleClasses();

    protected abstract Class<?>[] objectifyEntityClasses();

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        System.out.println("intercept");
        Resolution resolution = context.proceed();
        ActionBean actionBean = context.getActionBean();
        injectMembers(actionBean);
        return resolution;
    }

    protected void injectMembers(Object obj) {
        injector.injectMembers(obj);
    }
}
