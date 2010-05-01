package org.cloudme.runtrack.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.cloudme.runtrack.model.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class RouteRepositoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testRepositoryTwigPersist() {
        Repository<Route> repo = new Repository<Route>(Route.class);
		Route route = new Route();
		route.setDistance(10.5F);
		route.setLocation("Hamburg DE");
		route.setName("Kleine Runde");
		route.setType("Running");
        repo.save(route);
		System.out.println(route.getId());
        Route route1 = repo.load(route.getId());
		assertEquals("Kleine Runde", route1.getName());
        repo.delete(route1);
        assertNull(repo.load(route.getId()));
	}
}
