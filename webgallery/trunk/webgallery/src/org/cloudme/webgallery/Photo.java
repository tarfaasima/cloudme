package org.cloudme.webgallery;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Photo implements IdObject<String> {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	// private Key id;
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;
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

	public void setId(String id) {
        this.id = id;
    }

	public String getId() {
        return id;
    }
}
