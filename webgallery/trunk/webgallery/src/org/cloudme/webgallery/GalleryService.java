package org.cloudme.webgallery;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GalleryService {
    @Autowired
    private GalleryDao galleryDao;

    public void create(Gallery gallery) {
        galleryDao.save(gallery);
    }
    
    public Collection<Gallery> listAll() {
        return galleryDao.findAll();
    }
}
