package org.cloudme.webgallery.model;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings( "serial" )
@Indexed
public class ScaledPhotoData implements IdObject<Long> {
    @Id
    private Long id;
    private Long photoId;
    @Unindexed
    private Blob data;
    private String format;
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
