package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.persistence.AlbumRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JdoAlbumRepository extends AbstractJdoRepository<Long, Album> implements AlbumRepository {
    public JdoAlbumRepository() {
        super(Album.class);
	}
}
