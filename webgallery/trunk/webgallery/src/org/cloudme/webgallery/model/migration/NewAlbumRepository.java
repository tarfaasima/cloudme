package org.cloudme.webgallery.model.migration;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.persistence.jdo.AbstractJdoRepository;

public class NewAlbumRepository extends AbstractJdoRepository<Long, Album> {
    public NewAlbumRepository() {
        super(Album.class);
    }
}
