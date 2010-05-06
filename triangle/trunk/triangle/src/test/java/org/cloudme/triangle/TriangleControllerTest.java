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
        assertEquals(5, attributes.size());
        assertEquals("Name", attributes.iterator().next().getLabel());

        assertValidationFailure(entity, new TestEntity());

        final TestEntity testEntity = new TestEntity();
        testEntity.setName("Max");
        testEntity.setNoX("abc");
        testEntity.setAge(32);
        testEntity.setActive(true);
        entity.validate(testEntity);

        testEntity.setNoX("abxc");
        assertValidationFailure(entity, testEntity);

        testEntity.setNoX("abc");
        testEntity.setAge(5);
        assertValidationFailure(entity, testEntity);

        testEntity.setAge(32);
        testEntity.setActive(false);
        assertValidationFailure(entity, testEntity);

        testEntity.setActive(true);
    }

    private static void assertValidationFailure(final Entity entity, final Object object) {
        try {
            entity.validate(object);
            fail();
        } catch (final ValidationException e) {
            // expected
        }
    }
}
