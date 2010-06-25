package org.cloudme.webgallery.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;


@SuppressWarnings( "serial" )
@Indexed
public class Photo implements IdObject<Long> {
    @Unindexed
	private String contentType;
    @Unindexed
	private String fileName;
    @Id
	private Long id;
    @Unindexed
	private String name;
    @Unindexed
	private long size;
	private Long albumId;
    @Unindexed
	private Float cropBalance = 0.5f;
    @Transient
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
