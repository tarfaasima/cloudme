package org.cloudme.webgallery.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Photo implements IdObject<Long> {
	@Persistent
	private String contentType;
	@Persistent
	private String fileName;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String name;
	@Persistent
	private long size;
	@Persistent
	private Long albumId;
	@Persistent
	private Float cropBalance = 0.5f;
	@NotPersistent
	private PhotoData photoData;

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getId() {
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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setPhotoData(PhotoData photoData) {
		this.photoData = photoData;
	}

	public PhotoData getPhotoData() {
		return photoData;
	}

    public void setCropBalance(Float cropBalance) {
        this.cropBalance = cropBalance;
    }

    public Float getCropBalance() {
        return cropBalance == null ? 0.5f : cropBalance;
    }

}
