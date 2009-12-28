package org.cloudme.webgallery.service;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.persistence.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService extends AbstractService<String, Album> {
    @Autowired
    protected AlbumService(AlbumRepository repository) {
        super(repository);
    }
    
}
