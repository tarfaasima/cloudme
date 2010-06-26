package org.cloudme.webgallery.stripes.extensions;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.webgallery.guice.WebgalleryModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class GuiceInterceptor implements Interceptor {
    private Injector injector;

    public GuiceInterceptor() {
        injector = Guice.createInjector(new WebgalleryModule());
    }

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        Resolution resolution = context.proceed();
        injector.injectMembers(context.getActionBean());
        return resolution;
    }
}
