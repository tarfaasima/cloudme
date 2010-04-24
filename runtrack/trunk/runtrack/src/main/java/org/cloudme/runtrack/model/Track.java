package org.cloudme.runtrack.model;

public class Track {
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

	@Override
	public String toString() {
		return name + " (" + distance + " km)";
	}
}
