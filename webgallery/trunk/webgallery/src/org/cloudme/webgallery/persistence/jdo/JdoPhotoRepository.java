package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Photo;

public class JdoPhotoRepository extends AbstractJdoRepository<String, Photo> {
    public JdoPhotoRepository() {
        super(Photo.class, "name asc");
    }
}
