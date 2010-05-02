package org.cloudme.runtrack.model;

import javax.persistence.Id;

import com.vercer.engine.persist.annotation.Key;

public class Route {
    @Key
    @Id
	private Long id;
	private String name;
	private float distance;
	private String type;
	private String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
