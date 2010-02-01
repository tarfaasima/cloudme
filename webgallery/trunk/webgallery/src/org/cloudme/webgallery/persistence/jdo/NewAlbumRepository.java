package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.model.Album;
import org.springframework.stereotype.Repository;

@Repository
public class NewAlbumRepository extends AbstractJdoRepository<Long, Album> {
    public NewAlbumRepository() {
        super(Album.class);
    }
}
