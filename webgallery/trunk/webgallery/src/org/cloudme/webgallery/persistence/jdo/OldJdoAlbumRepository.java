package org.cloudme.webgallery.persistence.jdo;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.persistence.OldAlbumRepository;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public class OldJdoAlbumRepository extends AbstractJdoRepository<String, Album> implements OldAlbumRepository {
    public OldJdoAlbumRepository() {
        super(Album.class);
    }
}
