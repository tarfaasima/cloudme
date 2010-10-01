package org.cloudme.loclist.test;

import org.cloudme.loclist.guice.LoclistModule;
import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class AbstractServiceTestCase {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
                new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        Injector injector = Guice.createInjector(new LoclistModule());
        injector.injectMembers(this);
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
}
