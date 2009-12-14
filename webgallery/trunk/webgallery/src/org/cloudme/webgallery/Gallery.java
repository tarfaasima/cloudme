package org.cloudme.webgallery;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Gallery implements IdObject<Long> {
    @Persistent
    private String description;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private Long id;
    @Persistent
    private String name;
    @Persistent(mappedBy = "gallery")
//    @Persistent
    private List<Photo> photos;

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
    
    public void addPhoto(Photo photo) {
        if (photos == null) {
            photos = new ArrayList<Photo>();
        }
        photo.setGallery(this);
        photos.add(photo);
    }
}
