package org.cloudme.webgallery.model.migration;

import java.util.Collection;
import java.util.Date;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.persistence.OldAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoDataRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("deprecation")
@Service
public class MigrationService {
    @Autowired
    MigrationFlagRepository migrationFlagRepository;
    @Autowired
    OldAlbumRepository albumRepository;
    private final AlbumMigrator albumMigrator = new AlbumMigrator();
    @Autowired
    JdoAlbumRepository newAlbumRepository;
    @Autowired
    JdoPhotoRepository newPhotoRepository;
    @Autowired
    JdoPhotoDataRepository jdoPhotoDataRepository;
    private final StringBuilder log = new StringBuilder();
    
    public synchronized String migrate() {
        acquireLock();
        Collection<Album> albums = albumRepository.findAll();
        for (Album album : albums) {
            albumMigrator.migrate(log, newAlbumRepository, newPhotoRepository, jdoPhotoDataRepository, album.getDescription(), album.getName(), album.getPhotos());
            albumRepository.delete(album.getId());
        }
        releaseLock();
        return log.toString();
    }

    private void releaseLock() {
        Collection<MigrationFlag> flags = migrationFlagRepository.findAll();
        if (flags.isEmpty()) {
            throw new IllegalStateException("No lock flag.");
        }
        for (MigrationFlag flag : flags) {
            migrationFlagRepository.delete(flag.getId());
        }
    }

    private void acquireLock() {
        if (!migrationFlagRepository.findAll().isEmpty()) {
            throw new IllegalStateException("Lock already acquired.");
        }
        MigrationFlag flag = new MigrationFlag();
        flag.setTimestamp(new Date());
        migrationFlagRepository.save(flag);
    }
}
