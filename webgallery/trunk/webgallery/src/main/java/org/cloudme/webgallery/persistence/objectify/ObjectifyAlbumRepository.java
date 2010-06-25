package org.cloudme.webgallery.persistence.objectify;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.persistence.AlbumRepository;

public class ObjectifyAlbumRepository extends BaseObjectifyRepository<Album>
        implements AlbumRepository {
    public ObjectifyAlbumRepository() {
        super(Album.class);
    }
}
