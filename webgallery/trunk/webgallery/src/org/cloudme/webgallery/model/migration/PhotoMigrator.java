package org.cloudme.webgallery.model.migration;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoDataRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;

public class PhotoMigrator {
    private final PhotoDataMigrator photoDataMigrator = new PhotoDataMigrator();
    
    public void migrate(StringBuilder log, JdoPhotoRepository newPhotoRepository, JdoPhotoDataRepository jdoPhotoDataRepository, Long albumId, String contentType, byte[] data, String fileName, String name, long size) {
        Photo photo = new Photo();
        photo.setAlbumId(albumId);
        photo.setContentType(contentType);
        photo.setFileName(fileName);
        photo.setName(name);
        photo.setSize(size);
        newPhotoRepository.save(photo);
        photoDataMigrator.migrate(log, jdoPhotoDataRepository, photo.getId(), data);
        log.append(ToStringBuilder.reflectionToString(photo) + "\n");
    }
}
