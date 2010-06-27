package org.cloudme.webgallery.stripes.action;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.cloudme.webgallery.guice.WebgalleryModule;
import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.AlbumRepository;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class HomeActionBeanTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private HomeActionBean actionBean;
    private Album album;
    private Photo photo;

    @Before
    public void setUp() {
        helper.setUp();
        Injector injector = Guice.createInjector(new WebgalleryModule());
        AlbumRepository albumRepository = injector
                .getInstance(AlbumRepository.class);
        PhotoRepository photoRepository = injector
                .getInstance(PhotoRepository.class);

        album = new Album();
        album.setName("Test Album");
        albumRepository.save(album);

        photo = new Photo();
        photo.setAlbumId(album.getId());
        photo.setName("Test Photo");
        photoRepository.save(photo);

        actionBean = new HomeActionBean();
        injector.injectMembers(actionBean);
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testGetAlbums() {
        Collection<Album> albums = actionBean.getAlbums();
        assertEquals(1, albums.size());
        assertEquals(album.getId(), albums.iterator().next().getId());
    }

    @Test
    public void testGetRandomPhotoId() {
        assertEquals(photo.getId(), (Long) actionBean.getRandomPhotoId());
    }

}
