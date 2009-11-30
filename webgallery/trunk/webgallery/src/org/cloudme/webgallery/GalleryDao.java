package org.cloudme.webgallery;

import org.springframework.stereotype.Repository;

@Repository
public class GalleryDao {
    public void save(Gallery gallery) {
        System.out.println("save: " + gallery.getName());
    }
}
