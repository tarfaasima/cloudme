package org.cloudme.triangle.objectify;

import javax.persistence.Id;

import org.cloudme.triangle.EntityResolver;
import org.cloudme.triangle.TriangleController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ObjectifyEntityResolverTest {
    public static class TestEntity {
        @Id
        private long id;

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }

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
    public void testObjectify() {
        EntityResolver resolver = new ObjectifyEntityResolver();
        TriangleController controller = new TriangleController();
        controller.setEntityResolver(resolver);
        controller.addEntity(TestEntity.class);

        TestEntity testEntity = new TestEntity();
        resolver.put(testEntity);
        System.out.println(testEntity.getId());
    }
}
