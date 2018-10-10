package se.lth.base.server.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ride {
	private Location departureLocation;
	private Location arrivalLocation;
	private List<User> allTravelers;
	private String arrivalTime;
	private String departureTime;
	private int carSize;
	private int id;
	private int driverId;
	
	public Ride(int id, Location departureLocation, Location arrivalLocation, String departureTime, String arrivalTime, int size, int driverId) {
		this.id = id;
		this.arrivalLocation = arrivalLocation;
		this.departureLocation = departureLocation;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.carSize = size;
		this.allTravelers = new ArrayList<User>();
		this.driverId = driverId;
	}
	
	public int getCarSize() {
		return carSize;
	}
	
	public List<User> getTravelerList(){
		return allTravelers;
	}
	
	public boolean addTraveler(User u) {
		return allTravelers.add(u);
	}
	
	public boolean removeTraveler(User u) {
		return allTravelers.remove(u);
	}
	
	public Location getArrivalLocation() {
		return arrivalLocation;
	}
	
	public Location getDepartureLocation() {
		return departureLocation;
	}
	
	public String getArrivalTime() {
		return arrivalTime;
	}
	
	public String getDepartureTime() {
		return departureTime;
	}
	
	public int getID() {
		return id;
	}
}
