package org.cloudme.roadmap.stripes.extensions;

import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.roadmap.application.ApplicationModule;
import org.cloudme.sugar.AbstractActionBeanResolutionInterceptor;

import com.google.inject.Module;

@Intercepts( LifecycleStage.ActionBeanResolution )
public class ActionBeanResolutionInterceptor extends AbstractActionBeanResolutionInterceptor {
    private static final Module[] MODULES = { new ApplicationModule() };

    @Override
    protected Module[] createModules() {
        return MODULES;
    }
}
