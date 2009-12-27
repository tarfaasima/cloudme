package org.cloudme.webgallery;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.core.style.ToStringCreator;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Photo implements IdObject<String> {
    @Persistent
    private String contentType;
    @Persistent
    private Blob data;
    @Persistent
    private String fileName;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;
    @Persistent
    private String name;
    @Persistent
    private long size;

    public String getContentType() {
        return contentType;
    }

    public Blob getData() {
        return data;
    }

    public byte[] getDataAsArray() {
        return data.getBytes();
    }

    public String getFileName() {
        return fileName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public void setDataAsArray(byte[] data) {
        System.out.println("size: " + data.length);
        this.data = new Blob(data);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("name", name).toString();
    }
}
