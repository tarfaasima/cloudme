// Copyright 2010 Moritz Petersen
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
        private final Collection<Entity<?>> entities = new ArrayList<Entity<?>>();

        @Override
        public void addEntity(Entity<?> entity) {
            entities.add(entity);
        }
    }

    @SuppressWarnings( "unchecked" )
    @Test
    public void testInit() {
        Locale.setDefault(Locale.GERMANY);
        final TriangleController controller = new TriangleController();
        final TestEntityResolver resolver = new TestEntityResolver();
        controller.setEntityResolver(resolver);
        controller.addEntity(TestEntity.class);
        final Entity<TestEntity> entity = (Entity<TestEntity>) resolver.entities
                .iterator().next();
        assertEquals("TestEntity", entity.getName());
        assertEquals(TestEntity.class, entity.getType());
        assertEquals("Test", entity.getLabel());
        assertTrue(entity.isMainEntity());
        assertEquals(entity, controller.getMainEntity());
        final Collection<Attribute<TestEntity, ?>> attributes = entity
                .getAttributes();
        assertEquals(7, attributes.size());
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

        testEntity.setEmail("mail@example com");
        assertValidationFailure(entity, testEntity);

        testEntity.setEmail("mail@example.com");
        entity.validate(testEntity);

        entity.getAttribute("entryDate").convert(testEntity, "15.8.2007");
        assertEquals("15.08.2007", entity.getAttribute("entryDate").format(testEntity));
    }

    private static void assertValidationFailure(final Entity<TestEntity> entity,
            final TestEntity object) {
        try {
            entity.validate(object);
            fail();
        } catch (final ValidationException e) {
            // expected
        }
    }
}
