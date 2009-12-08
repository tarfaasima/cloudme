package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Photo;

public class JdoPhotoRepository extends AbstractJdoRepository<Photo> {
    public JdoPhotoRepository() {
        super(Photo.class, "name asc");
    }
}
