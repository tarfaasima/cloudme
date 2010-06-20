package org.cloudme.webgallery.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@SuppressWarnings( "serial" )
@PersistenceCapable( identityType = IdentityType.APPLICATION )
public class ScaledPhotoData implements IdObject<Long> {
    @PrimaryKey
    @Persistent( valueStrategy = IdGeneratorStrategy.IDENTITY )
    private Long id;
    @Persistent
    private Long photoId;
    @Persistent
    private Blob data;
    @Persistent
    private String format;
    @Persistent
    private String type;

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Blob getData() {
        return data;
    }

    public byte[] getDataAsArray() {
        return data.getBytes();
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public void setDataAsArray(byte[] data) {
        this.data = new Blob(data);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setId(Long internalId) {
        this.id = internalId;
    }

    public Long getId() {
        return id;
    }
}
