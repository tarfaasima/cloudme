package org.cloudme.notepad.stripes.extensions;

import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.sugar.AbstractActionBeanResolutionInterceptor;

import com.google.inject.Module;

@Intercepts( LifecycleStage.ActionBeanResolution )
public class ActionBeanResolutionInterceptor extends AbstractActionBeanResolutionInterceptor {
    @Override
    protected Module[] createModules() {
		return GuiceModules.MODULES;
    }
}
