package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Photo;

import com.google.appengine.api.datastore.Key;

public class JdoPhotoRepository extends AbstractJdoRepository<Key, Photo> {
    public JdoPhotoRepository() {
        super(Photo.class, "name asc");
    }
}
