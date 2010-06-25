package org.cloudme.webgallery.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;


@SuppressWarnings( "serial" )
public class Album implements IdObject<Long> {
    @Id
	private Long id;
    @Unindexed
	private String name;
    @Unindexed
	private String description;
    @Unindexed
	private int photoCount = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	public int getPhotoCount() {
		return photoCount;
	}
}
