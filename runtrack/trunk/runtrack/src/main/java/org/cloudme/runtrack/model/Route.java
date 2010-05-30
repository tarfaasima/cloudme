package org.cloudme.runtrack.model;

import javax.persistence.Id;

public class Route {
    @Id
	private Long id;
	private String name;
	private float distance;

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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
