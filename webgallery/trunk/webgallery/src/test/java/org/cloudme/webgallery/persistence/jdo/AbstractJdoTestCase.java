package org.cloudme.webgallery.persistence.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.sourceforge.stripes.action.After;

import org.cloudme.webgallery.model.IdObject;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.PMF;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class AbstractJdoTestCase<K, T extends IdObject<K>> {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    protected AbstractJdoRepository<K, T> repo;

    public abstract T createEntity();

    public abstract AbstractJdoRepository<K, T> createRepository();

    @Before
    public void setUp() {
        helper.setUp();
        repo = createRepository();
        repo.setPersistenceManagerFactory(PMF.get());
        assertRepoSize(0);
    }
    
    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testCreate() {
        create();
        assertRepoSize(1);

        T t2 = repo.findAll().iterator().next();
        T t3 = repo.find(t2.getId());
        assertNotNull(t3);
    }

    @Test
    public void testDelete() {
        create();
        assertRepoSize(1);
        
        T entity = repo.findAll().iterator().next();
        repo.delete(entity.getId());
        assertRepoSize(0);
    }
    
    protected void assertRepoSize(int expected) {
        assertEquals(expected, repo.findAll().size());
    }

    private void create() {
        T t1 = createEntity();
        repo.save(t1);
    }
}
