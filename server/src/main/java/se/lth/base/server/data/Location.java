package se.lth.base.server.data;

public class Location {
	private final float[] coordinate;
	private String name;
	
	public Location(String name, float[] coordinate) {
		this.name = name;
		this.coordinate = coordinate;
	}
	
	public String returnLocation() {
		return name;
	}
	
	public float[] returnCoordinate() {
		return coordinate;
	}
}
