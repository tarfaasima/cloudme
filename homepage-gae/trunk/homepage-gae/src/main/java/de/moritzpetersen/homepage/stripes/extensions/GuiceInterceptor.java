package de.moritzpetersen.homepage.stripes.extensions;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.moritzpetersen.homepage.guice.HomepageModule;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class GuiceInterceptor implements Interceptor {
    private final Injector injector;

    public GuiceInterceptor() {
        injector = Guice.createInjector(new HomepageModule());
    }

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        Resolution resolution = context.proceed();
        injector.injectMembers(context.getActionBean());
        return resolution;
    }
}
