package org.cloudme.loclist.stripes.extensions;

import org.cloudme.gaestripes.GuiceInterceptor;
import org.cloudme.loclist.guice.LoclistModule;

import com.google.inject.Module;

public class LoclistInterceptor extends GuiceInterceptor {
    @Override
    protected Module createModule() {
        return new LoclistModule();
    }
}
