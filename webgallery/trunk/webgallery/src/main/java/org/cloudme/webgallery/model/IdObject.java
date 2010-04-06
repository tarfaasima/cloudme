package org.cloudme.webgallery.model;

import java.io.Serializable;

public interface IdObject<K> extends Serializable {
    public K getId();
    public void setId(K id);
}
