package org.cloudme.uploader;

import java.util.Date;

import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings("serial")
@Unindexed
public class Item extends Entity {
    private byte[] data;
    private String contentType;
    private String name;
    private Date uploadedAt;
    private String uploadedBy;

    public byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

    public String getName() {
        return name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date dateCreated) {
        this.uploadedAt = dateCreated;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String clientIp) {
        this.uploadedBy = clientIp;
    }
}
