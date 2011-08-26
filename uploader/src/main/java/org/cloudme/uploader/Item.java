package org.cloudme.uploader;

import org.cloudme.sugar.Entity;

public class Item extends Entity {
    private byte[] data;
    private String contentType;
    private String name;

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
}
