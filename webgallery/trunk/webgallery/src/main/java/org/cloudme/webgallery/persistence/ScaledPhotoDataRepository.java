package org.cloudme.webgallery.persistence;

import java.util.Collection;

import org.cloudme.webgallery.model.ScaledPhotoData;

public interface ScaledPhotoDataRepository extends
        Repository<Long, ScaledPhotoData> {

    Collection<ScaledPhotoData> find(Long photoId, String format, String type);

    void deleteByPhotoId(long photoId);
}
