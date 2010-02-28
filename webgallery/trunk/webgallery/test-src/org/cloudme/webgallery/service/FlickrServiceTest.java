package org.cloudme.webgallery.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.cache.CacheException;

import net.sourceforge.stripes.action.After;

import org.cloudme.webgallery.cache.gae.GaeCacheService;
import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.service.FlickrService.Perms;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FlickrServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testCreateLoginLink() throws CacheException {
        FlickrService service = new FlickrService(null) {
            @Override
            public Collection<FlickrMetaData> findAll() {
                FlickrMetaData metaData = new FlickrMetaData();
                metaData.setKey("1234");
                metaData.setSecret("abcd");
                return Arrays.asList(metaData);
            }
        };
        service.cacheService = new GaeCacheService();

        assertEquals("http://flickr.com/services/auth/?api_key=1234&perms=write&api_sig=54f77b1e0d1f3886c1ce6c61d13a710e", service.createLoginLink(Perms.WRITE));
    }

    @Test
    public void testCreateLoginLinkWjthoutMetaData() throws CacheException {
        FlickrService service = new FlickrService(null) {
            @Override
            public Collection<FlickrMetaData> findAll() {
                return Collections.emptyList();
            }
        };
        service.cacheService = new GaeCacheService();

        assertEquals("", service.createLoginLink(Perms.WRITE));
    }
}
