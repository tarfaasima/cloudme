package org.cloudme.webgallery.service;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.persistence.Repository;
import org.cloudme.webgallery.persistence.jdo.JdoAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService extends GenericService<String, Album> {
    @Autowired
    private JdoAlbumRepository repository;
    
    @Override
    protected Repository<String, Album> getRepository() {
        return repository;
    }
}
