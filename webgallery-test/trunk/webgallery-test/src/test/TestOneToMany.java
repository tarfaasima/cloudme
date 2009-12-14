package test;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Test;

import com.google.appengine.tools.development.LocalDatastoreTestCase;
import com.google.appengine.tools.development.PMF;

public class TestOneToMany extends LocalDatastoreTestCase {
	@Test
	public void testOneToMany() {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		Action<Void, Employee> save = new Action<Void, Employee>() {
			@Override
			Void execute(Employee e) {
				pm.makePersistent(e);
				return null;
			}
		};
		Action<Collection<Employee>, Void> fetchAll = new Action<Collection<Employee>, Void>() {
			@SuppressWarnings("unchecked")
			@Override
			Collection<Employee> execute(Void v) {
				Collection<Employee> items = (Collection<Employee>) pm.newQuery(Employee.class).execute();
				items = pm.detachCopyAll(items);
				return items;
			};
		};
		assertFetchAll(save, fetchAll);
	}

	@Test
	public void testWithRepository() {
		final JdoEmployeeRepository repo = new JdoEmployeeRepository();
//		repo.setPersistenceManagerFactory(PMF.get());
		repo.setPersistenceManager(PMF.get().getPersistenceManager());
		Action<Void, Employee> save = new Action<Void, Employee>() {
			@Override
			Void execute(Employee p) {
				repo.save(p);
				return null;
			}
		};
		Action<Collection<Employee>, Void> fetchAll = new Action<Collection<Employee>, Void>() {
			@Override
			Collection<Employee> execute(Void v) {
				Collection<Employee> items = repo.findAll();
				return items;
			}
		};
		assertFetchAll(save, fetchAll);
	}

	private void assertFetchAll(Action<Void, Employee> save, Action<Collection<Employee>, Void> fetchAll) {
		Employee e1 = new Employee();
		ContactInfo c1 = new ContactInfo();
		c1.setStreetAddress("Infinite Loop 1");
		e1.addContactInfo(c1);
		save.execute(e1);
		Collection<Employee> employees = fetchAll.execute(null);
		assertEquals(1, employees.size());
		Employee e2 = employees.iterator().next();
		List<ContactInfo> c2 = e2.getContactInfos();
		assertEquals("Infinite Loop 1", c2.get(0).getStreetAddress());
	}

	private abstract class Action<R, P> {
		abstract R execute(P p);
	}
}
