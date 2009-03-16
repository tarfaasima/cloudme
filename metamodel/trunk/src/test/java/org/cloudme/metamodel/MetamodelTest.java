package org.cloudme.metamodel;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

public class MetamodelTest {
    @Test
    public void testMetamodel() {
        Metamodel metamodel = MetamodelFactory.newInstance().newMetamodel();
        assertEquals(0, metamodel.getEntities().size());

        Entity systemEntity = metamodel.newEntity("system");
        Collection<Entity> entities = metamodel.getEntities();
        assertEquals(1, entities.size());
        assertEquals("system", entities.iterator().next().getName());
        assertEquals(systemEntity, entities.iterator().next());
        systemEntity.addProperty("name", Type.STRING);
        systemEntity.addProperty("vendor", Type.STRING);
        systemEntity.addProperty("version", Type.DECIMAL);
        Instance lightroom = systemEntity.newInstance();
        lightroom.setValue("name", "Lightroom");
        lightroom.setValue("vendor", "Adobe");
        lightroom.setValue("version", "1");
        lightroom.validate();
    }
}
