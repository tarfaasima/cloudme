package org.cloudme.webgallery.model;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.cloudme.webgallery.IdObject;

import com.google.appengine.api.datastore.Blob;

public class PhotoData implements IdObject<Long> {
	@PrimaryKey
	private Long id;
	@Persistent
	private Blob data;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
