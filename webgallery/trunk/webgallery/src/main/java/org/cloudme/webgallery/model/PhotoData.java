package org.cloudme.webgallery.model;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings( "serial" )
@Indexed
public class PhotoData implements IdObject<Long> {
    @Id
	private Long id;
    @Unindexed
	private Blob data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public void setData(Blob data) {
        this.data = data;
    }

    public Blob getData() {
        return data;
    }
    
    public void setDataAsArray(byte[] data) {
        this.data = new Blob(data);
    }
    
    public byte[] getDataAsArray() {
        return data.getBytes();
    }
}
