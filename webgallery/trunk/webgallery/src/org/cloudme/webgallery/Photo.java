package org.cloudme.webgallery;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Photo {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    @Persistent
    private Gallery gallery;
    @Persistent
    private Blob imageData;
    @Persistent
    private String name;
    @Persistent
    private long size;

    public Blob getImageData() {
        return imageData;
    }

    public byte[] getImageDataAsArray() {
        return imageData.getBytes();
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public void setImageData(Blob imageData) {
        this.imageData = imageData;
    }

    public void setImageDataAsArray(byte[] bytes) {
        imageData = new Blob(bytes);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }
}
