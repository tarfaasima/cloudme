package org.cloudme.metamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.cloudme.metamodel.util.ValidationError;
import org.junit.Test;

public class MetamodelTest {
    @Test
    public void testMetamodel() {
        Metamodel metamodel = MetamodelFactory.newInstance().newMetamodel();
        assertEquals("Expected an empty model.", 0, metamodel.getEntities().size());

        Entity systemEntity = metamodel.newEntity("system");
        Collection<Entity> entities = metamodel.getEntities();
        assertEquals("Expected only one entity.", 1, entities.size());
        assertEquals("The entitie's name should be 'system'.", "system", entities.iterator().next().getName());
        assertEquals("The entity should equal the system entity.", systemEntity, entities.iterator().next());
        systemEntity.addProperty("name", Type.STRING, "Name");
        systemEntity.addProperty("vendor", Type.STRING, "Vendor");
        systemEntity.addProperty("version", Type.DECIMAL, "Version");
        
        Collection<Property> properties = systemEntity.getProperties();
        assertEquals(3, properties.size());
        Property property = properties.iterator().next();
        assertEquals("name", property.getName());
        assertEquals(Type.STRING, property.getType());
        assertEquals("Name", property.getLabel());
        
        Instance lightroom = systemEntity.newInstance();
        lightroom.setValue("name", "Lightroom");
        lightroom.setValue("vendor", "Adobe");
        lightroom.setValue("version", "1");
        assertTrue(lightroom.validate().isEmpty());
        
        lightroom.setValue("version", "1a");
        Collection<ValidationError> errors = lightroom.validate();
        assertEquals(1, errors.size());
        ValidationError error = errors.iterator().next();
        assertEquals("version", error.getProperty());
        assertEquals("1a", error.getValue());
        
        lightroom.setValue("version", "1");
        systemEntity.addProperty("os", Type.STRING, "Operating System");
        lightroom.setValue("os", "Mac OS X");
        System.out.println("DEBUG: " + metamodel);
        System.out.println("DEBUG: " + lightroom);
        assertTrue(lightroom.validate().isEmpty());
    }
}
