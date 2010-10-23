package org.cloudme.loclist.stripes.extensions;

import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.gaestripes.GuiceInterceptor;
import org.cloudme.loclist.guice.LoclistModule;

import com.google.inject.Module;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class LoclistInterceptor extends GuiceInterceptor {
    @Override
    protected Module createModule() {
        return new LoclistModule();
    }
}
