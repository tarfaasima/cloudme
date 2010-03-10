package org.cloudme.webgallery.util;

import static org.junit.Assert.assertEquals;

import org.cloudme.webgallery.model.Photo;
import org.junit.Test;


public class UrlUtilsTest {
    @Test
    public void testCreateUrlForPhoto() {
        Photo photo = new Photo();
		photo.setAlbumId(1234L);
		photo.setId(5678L);
		assertEquals("http://some.url.org/gallery/album/1234/photo/5678", UrlUtils.createUrl("some.url.org", 80, photo));
		assertEquals("http://some.url.org:8888/gallery/album/1234/photo/5678", UrlUtils.createUrl("some.url.org", 8888,
				photo));
		assertEquals("http://some.url.org:81/gallery/album/1234/photo/5678", UrlUtils.createUrl("some.url.org", 81,
				photo));
		assertEquals("http://some.url.org:79/gallery/album/1234/photo/5678", UrlUtils.createUrl("some.url.org", 79,
				photo));
    }
}
