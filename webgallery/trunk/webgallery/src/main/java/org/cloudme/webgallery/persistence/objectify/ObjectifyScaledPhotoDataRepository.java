package org.cloudme.webgallery.persistence.objectify;

import java.util.Collection;

import org.cloudme.webgallery.model.ScaledPhotoData;
import org.cloudme.webgallery.persistence.ScaledPhotoDataRepository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

public class ObjectifyScaledPhotoDataRepository extends
        BaseObjectifyRepository<ScaledPhotoData> implements
        ScaledPhotoDataRepository {

    public ObjectifyScaledPhotoDataRepository() {
        super(ScaledPhotoData.class);
    }

    @Override
    public void deleteByPhotoId(final long photoId) {
        execute(new Callback<Object>() {
            @Override
            protected Object execute(Objectify ofy) {
                Query<ScaledPhotoData> query = ofy.query(ScaledPhotoData.class);
                ofy.delete(query.filter("photoId", photoId).fetchKeys());
                return null;
            }
        });
    }

    @Override
    public Collection<ScaledPhotoData> find(final Long photoId,
            final String format,
            final String type) {
        return execute(new Callback<Collection<ScaledPhotoData>>() {
            @Override
            protected Collection<ScaledPhotoData> execute(Objectify ofy) {
                Query<ScaledPhotoData> query = ofy.query(ScaledPhotoData.class);
                return query.filter("photoId", photoId)
                        .filter("format", format).filter("type", type).list();
            }
        });
    }

}
