package org.cloudme.webgallery.persistence.objectify;

import java.util.Collection;
import java.util.List;

import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.PhotoRepository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

public class ObjectifyPhotoRepository extends BaseObjectifyRepository<Photo>
        implements PhotoRepository {

    public ObjectifyPhotoRepository() {
        super(Photo.class);
    }

    @Override
    public int countPhotosByAlbumId(final Long albumId) {
        return execute(new Callback<Integer>() {
            @Override
            protected Integer execute(Objectify ofy) {
                return queryByAlbumId(ofy, albumId).countAll();
            }
        });
    }

    @Override
    public Collection<Photo> findByAlbumId(final Long albumId) {
        return execute(new Callback<Collection<Photo>>() {
            @Override
            protected Collection<Photo> execute(Objectify ofy) {
                return queryByAlbumId(ofy, albumId).list();
            }
        });
    }

    private Query<Photo> queryByAlbumId(Objectify ofy, final Long albumId) {
        return ofy.query(Photo.class).filter("albumId", albumId);
    }

    @Override
    public Long getRandomPhotoId() {
        return execute(new Callback<Long>() {
            @Override
            protected Long execute(Objectify ofy) {
                List<Key<Photo>> keys = ofy.query(Photo.class).listKeys();
                if (keys.isEmpty()) {
                    return -1L;
                }
                int random = (int) (Math.random() * keys.size());
                return keys.get(random).getId();
            }
        });
    }
}
