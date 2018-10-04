package se.lth.base.server.data;

import java.util.List;

public class Ride {
	private Location departureLocation;
	private Location arrivalLocation;
	private List<User> allTravelers;
	private Date arrivalTime;
	private Date departureTime;
	private int carSize;
	private int id;
	
	public Ride(int id, Location aL, Location dL, Date aD, Date dD, int size) {
		this.id = id;
		arrivalLocation = aL;
		departureLocation = dL;
		arrivalTime = aD;
		departureTime = dD;
		carSize = size;
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
	
	public int getID() {
		return id;
	}
}
