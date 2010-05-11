package org.cloudme.triangle.objectify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import javax.persistence.Id;

import org.cloudme.triangle.Entity;
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
        private Long id;
        private String content;

        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
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
        final EntityResolver resolver = new ObjectifyEntityResolver();
        final TriangleController controller = new TriangleController();
        controller.setEntityResolver(resolver);
        controller.addEntity(TestEntity.class);
        final Entity<TestEntity> entity = controller.getEntity("TestEntity");

        final TestEntity testEntity = new TestEntity();
        testEntity.setContent("Hello world");
        resolver.put(testEntity);
        assertNotNull(testEntity.getId());

        final TestEntity testEntity2 = resolver.get(entity, testEntity.getId());
        assertEquals("Hello world", testEntity2.getContent());

        final Collection<TestEntity> entities = resolver.findAll(entity);
        assertEquals(1, entities.size());
        assertEquals("Hello world", entities.iterator().next().getContent());

        resolver.delete(entity, testEntity.getId());
        assertEquals(0, resolver.findAll(entity).size());

        testEntity.setContent("Foo bar");
        resolver.put(testEntity);
        assertEquals(testEntity2.getId(), testEntity.getId());
        testEntity.setId(null);
        resolver.put(testEntity);
        assertFalse(testEntity2.getId().equals(testEntity.getId()));
        assertEquals("Foo bar", resolver.findAll(entity).iterator().next().getContent());

    }
}
