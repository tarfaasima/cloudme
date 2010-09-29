package org.cloudme.loclist.location;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.guice.LoclistModule;
import org.cloudme.loclist.model.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class LocationServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig());
    @Inject
    private LocationService locationService;

    @Before
    public void init() {
        Injector injector = Guice.createInjector(new LoclistModule());
        injector.injectMembers(this);
    }

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testLocationService() {
        Location location1 = locationService.checkin(53.59984f, 9.710541f);
        Location location2 = locationService.checkin(53.59984f, 9.71054f);
        assertEquals(location1.getGeoPt(), location2.getGeoPt());
    }
}
