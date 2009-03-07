package org.cloudme.metamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TypeTest {
    @Test
    public void testSimple() {
        Type vendorType = new Type("Vendor");
        assertEquals("Vendor", vendorType.getName());
        vendorType.addAttribute(new Attribute("Name"));
        Instance vendor = new Instance(vendorType);
        vendor.setValue(new Attribute("Name"), "SAP");
        try {
            vendor.setValue(new Attribute("Test"), "SAP");
            fail("Exception expected");
        }
        catch (UndefinedAttributeException e) {
            // ok.
        }
        catch (Exception e) {
            fail("UndefinedAttributeException expected");
        }
        assertEquals("SAP", vendor.getValue(new Attribute("Name")));
    }
    
    @Test
    public void testHasAttribute() {
        Type type = new Type("T");
        type.addAttribute(new Attribute("A"));
        assertTrue(type.hasAttribute(new Attribute("A")));
    }
}
