package org.cloudme.webgallery.model.migration;

import java.util.Collection;
import java.util.Date;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.persistence.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MigrationService {
    @Autowired
    MigrationFlagRepository migrationFlagRepository;
    @Autowired
    AlbumRepository albumRepository;
    private final AlbumMigrator albumMigrator = new AlbumMigrator();
    @Autowired
    NewAlbumRepository newAlbumRepository;
    @Autowired
    NewPhotoRepository newPhotoRepository;
    @Autowired
    NewPhotoDataRepository newPhotoDataRepository;
    
    public synchronized void migrate() {
        acquireLock();
        Collection<Album> albums = albumRepository.findAll();
        for (Album album : albums) {
            albumMigrator.migrate(newAlbumRepository, newPhotoRepository, newPhotoDataRepository, album.getDescription(), album.getName(), album.getPhotos());
            albumRepository.delete(album.getId());
        }
        releaseLock();
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
