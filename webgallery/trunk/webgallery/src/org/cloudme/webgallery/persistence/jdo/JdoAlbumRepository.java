package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Album;
import org.springframework.stereotype.Repository;

@Repository
public class JdoAlbumRepository extends AbstractJdoRepository<String, Album> {
    public JdoAlbumRepository() {
        super(Album.class);
    }
}
