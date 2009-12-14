package test;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Test;

import com.google.appengine.tools.development.LocalDatastoreTestCase;
import com.google.appengine.tools.development.PMF;


public class TestOneToMany extends LocalDatastoreTestCase {
    @SuppressWarnings("unchecked")
    @Test
    public void testOneToMany() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        // Create employee and contact info
        Employee e1 = new Employee();
        ContactInfo c1 = new ContactInfo();
        c1.setStreetAddress("Infinite Loop 1");
        e1.addContactInfo(c1);
        pm.makePersistent(e1);
        // Fetch employees, check that 1 employee exists
        Collection<Employee> employees = (Collection<Employee>) pm.newQuery(Employee.class).execute();
        // Detach employees
        employees = pm.detachCopyAll(employees);
        pm.close();
        assertEquals(1, employees.size());
        // Fetch contact infos, check that 1 contact info exists
//        Collection<ContactInfo> contactInfos = (Collection<ContactInfo>) pm.newQuery(ContactInfo.class).execute();
//        assertEquals(1, contactInfos.size());
//        // Check that it's the contact info you have just defined
//        assertEquals("Infinite Loop 1", contactInfos.iterator().next().getStreetAddress());
        // Check the persistent employee has the contact info attached
        Employee e2 = employees.iterator().next();
        List<ContactInfo> c2 = e2.getContactInfos();
        assertEquals("Infinite Loop 1", c2.get(0).getStreetAddress());
    }
    
    @Test
    public void testWithRepository() {
        JdoEmployeeRepository repo = new JdoEmployeeRepository();
        repo.init(PMF.get());
        // Create employee and contact info
        Employee e1 = new Employee();
        ContactInfo c1 = new ContactInfo();
        c1.setStreetAddress("Infinite Loop 1");
        e1.addContactInfo(c1);
        repo.save(e1);
        // Fetch employees, check that 1 employee exists
        Collection<Employee> employees = repo.findAll();
        assertEquals(1, employees.size());
        // Fetch contact infos, check that 1 contact info exists
//        Collection<ContactInfo> contactInfos = (Collection<ContactInfo>) pm.newQuery(ContactInfo.class).execute();
//        assertEquals(1, contactInfos.size());
//        // Check that it's the contact info you have just defined
//        assertEquals("Infinite Loop 1", contactInfos.iterator().next().getStreetAddress());
        // Check the persistent employee has the contact info attached
        Employee e2 = employees.iterator().next();
        List<ContactInfo> c2 = e2.getContactInfos();
        assertEquals("Infinite Loop 1", c2.get(0).getStreetAddress());
    }
}
