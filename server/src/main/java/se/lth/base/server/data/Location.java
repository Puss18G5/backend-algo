package se.lth.base.server.data;

public class Location {
	private double lat;
	private double lon;
	private String name;
	
	public Location(String name, double latitude, double longitude) {
		this.name = name;
		lat = latitude;
		lon = longitude;
	}
	
	public String getLocation() {
		return name;
	}
	
	public double getLongitude() {
		return lon;
	}
	
	public double getLatitude() {
		return lat;
	}
}
