package org.cloudme.triangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.cloudme.triangle.validation.ValidationException;
import org.junit.Test;

public class TriangleControllerTest {
    public class TestEntityResolver implements EntityResolver {
        private final Collection<Entity> entities = new ArrayList<Entity>();

        @Override
        public void addEntity(Entity entity) {
            entities.add(entity);
        }
    }

    @Test
    public void testInit() {
        final TriangleController controller = new TriangleController();
        final TestEntityResolver resolver = new TestEntityResolver();
        controller.setEntityResolver(resolver);
        controller.addEntity(TestEntity.class);
        final Entity entity = resolver.entities.iterator().next();
        assertEquals("TestEntity", entity.getName());
        assertEquals(TestEntity.class, entity.getType());
        assertEquals("Test", entity.getLabel());
        assertTrue(entity.isMainEntity());
        assertEquals(entity, controller.getMainEntity());
        final Collection<Attribute> attributes = entity.getAttributes();
        assertEquals(1, attributes.size());
        assertEquals("Name", attributes.iterator().next().getLabel());

        try {
            entity.validate(new TestEntity());
            fail();
        } catch (final ValidationException e) {
            // expected
        } catch (final RuntimeException e) {
            throw e;
        }
    }
}
