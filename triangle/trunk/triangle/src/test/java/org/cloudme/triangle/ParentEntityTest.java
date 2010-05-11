package org.cloudme.triangle;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ParentEntityTest {
    @Test
    public void testParentChildEntity() {
        TriangleController controller = new TriangleController();
        controller.addEntity(TestEntity.class, TestChildEntity.class);
        @SuppressWarnings( "unused" )
        Entity<TestEntity> entity = controller.getMainEntity();
        fail("not yet implemented.");
    }
}
