package org.cloudme.triangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

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
        Locale.setDefault(Locale.GERMANY);
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
        assertEquals(6, attributes.size());
        assertEquals("Name", entity.getAttribute("name").getLabel());

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

        testEntity.setWeight(82F);
        assertEquals("82,0", entity.getAttribute("weight").format(testEntity));
        
        entity.getAttribute("weight").convert(testEntity, "94,3");
        assertEquals(94.3F, testEntity.getWeight(), 0.0001D);
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
