package org.cloudme.webgallery.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.action.After;

import org.cloudme.webgallery.cache.gae.GaeCacheService;
import org.cloudme.webgallery.guice.WebgalleryModule;
import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.persistence.FlickrMetaDataRepository;
import org.cloudme.webgallery.service.FlickrService.Perms;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FlickrServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig(), new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testCreateLoginLink() throws Exception {
        FlickrService service = new FlickrService(null) {
            @Override
            public Collection<FlickrMetaData> findAll() {
                FlickrMetaData metaData = new FlickrMetaData();
                metaData.setKey("1234");
                metaData.setSecret("abcd");
                return Arrays.asList(metaData);
            }
        };

        Injector injector = Guice.createInjector(new WebgalleryModule());
        injector.injectMembers(service);

        assertEquals("http://flickr.com/services/auth/?api_key=1234&perms=write&api_sig=54f77b1e0d1f3886c1ce6c61d13a710e", service.createLoginLink(Perms.WRITE));
    }

    @Test
    public void testCreateLoginLinkWithoutMetaData() throws Exception {
        FlickrService service = new FlickrService(null) {
            @Override
            public Collection<FlickrMetaData> findAll() {
                return Collections.emptyList();
            }
        };
        service.cacheService = new GaeCacheService();

        assertEquals("", service.createLoginLink(Perms.WRITE));
    }
    
    @Test
    public void testGetAndPut() throws Exception {
        class IdGen {
            private long nextId = 1;
            
            public synchronized long getNextId() {
                return nextId++;
            }
        }
        final IdGen idGen = new IdGen();
        FlickrService service = new FlickrService(new FlickrMetaDataRepository() {
            private final Map<Long, FlickrMetaData> store = new HashMap<Long, FlickrMetaData>();
            
            public void save(FlickrMetaData t) {
                if (t.getId() == null) {
                    t.setId(idGen.getNextId());
                }
                store.put(t.getId(), t);
            }
            
            public Collection<FlickrMetaData> findAll() {
                return store.values();
            }
            
            public FlickrMetaData find(Long id) {
                return store.get(id);
            }
            
            public void delete(Long id) {
                store.remove(id);
            }
        });
        service.cacheService = new GaeCacheService();
        assertNull(service.get());
        FlickrMetaData m1 = new FlickrMetaData();
        m1.setKey("abcd");
        m1.setSecret("1234");
        service.put(m1);
        FlickrMetaData m2 = service.get();
        assertNotNull(m2);
        assertEquals(m1.getId(), m2.getId());
        service.put(new FlickrMetaData());
        m2 = service.get();
        assertTrue(m2.getId() > 0);
        assertEquals(1, service.findAll().size());
    }
}
