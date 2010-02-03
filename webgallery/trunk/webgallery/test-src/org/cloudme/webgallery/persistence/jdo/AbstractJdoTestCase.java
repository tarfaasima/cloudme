package org.cloudme.webgallery.persistence.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.cloudme.webgallery.model.IdObject;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.LocalDatastoreTestCase;
import com.google.appengine.tools.development.PMF;

public abstract class AbstractJdoTestCase<K, T extends IdObject<K>> extends LocalDatastoreTestCase {
    protected AbstractJdoRepository<K, T> repo;

    public abstract T createEntity();

    public abstract AbstractJdoRepository<K, T> createRepository();

    @Override
    @Before
    public void setUp() {
        super.setUp();
        repo = createRepository();
        repo.setPersistenceManagerFactory(PMF.get());
        assertRepoSize(0);
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
