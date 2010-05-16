package org.cloudme.triangle;

import org.junit.Test;

public class ParentEntityTest {
    @Test
    public void testParentChildEntity() {
        TriangleController controller = new TriangleController();
        controller.register(MyEntity.class, MyChildEntity.class);
        @SuppressWarnings( "unused" )
        Entity<MyEntity> entity = controller.getMainEntity();
    }
}
