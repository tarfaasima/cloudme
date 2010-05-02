package org.cloudme.runtrack.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.cloudme.runtrack.model.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class RouteRepositoryTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testRepositoryObjectify() {
        Route route = new Route();
        route.setDistance(10.5F);
        route.setLocation("Hamburg DE");
        route.setName("Kleine Runde");
        route.setType("Running");

        Repository<Route> repository = new Repository<Route>(Route.class);
        repository.put(route);
        assertNotNull(route.getId());
        Route route1 = repository.find(route.getId());
        assertEquals(route.getName(), route1.getName());
        repository.delete(route1.getId());
        assertNull(repository.find(route.getId()));
    }
}
