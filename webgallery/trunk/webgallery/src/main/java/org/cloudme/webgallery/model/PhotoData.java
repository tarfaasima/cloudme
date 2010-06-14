package org.cloudme.webgallery.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@SuppressWarnings( "serial" )
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PhotoData implements IdObject<Long> {
	@PrimaryKey
	private Long id;
	@Persistent
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
