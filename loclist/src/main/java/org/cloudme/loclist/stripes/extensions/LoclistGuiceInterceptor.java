package org.cloudme.loclist.stripes.extensions;

import org.cloudme.loclist.guice.LoclistModule;
import org.cloudme.stripestools.GuiceInterceptor;

import com.google.inject.Module;

public class LoclistGuiceInterceptor extends GuiceInterceptor {
    @Override
    protected Module createModule() {
        return new LoclistModule();
    }
}
