package se.lth.base.server.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ride {
	private String departureLocation;
	private String arrivalLocation;
	private List<User> allTravelers;
	private String arrivalTime;
	private String departureTime;
	private int carSize;
	private int id;
	private int driverId;
	
	public Ride(String departureLocation, String arrivalLocation, 
			String departureTime, String arrivalTime, int size, int driverId) {
		this.arrivalLocation = arrivalLocation;
		this.departureLocation = departureLocation;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.carSize = size;
		this.allTravelers = new ArrayList<User>();
		this.driverId = driverId;
	}
	
	public Ride(String departureLocation, String arrivalLocation, 
			String departureTime, String arrivalTime) {
		this.arrivalLocation = arrivalLocation;
		this.departureLocation = departureLocation;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.allTravelers = new ArrayList<User>();	
	}
	
	public int getCarSize() {
		return carSize;
	}
	
	public List<User> getTravelerList(){
		return allTravelers;
	}
	
	public int getDriverId() {
		return driverId;
	}
	
	public boolean addTraveler(User u) {
		return allTravelers.add(u);
	}
	
	public boolean removeTraveler(User u) {
		return allTravelers.remove(u);
	}
	
	public String getArrivalLocation() {
		return arrivalLocation;
	}
	
	public String getDepartureLocation() {
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
	
	public Date departureTimeAsDate() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(departureTime);
		return date;
	}
	
	public Date arrivalTimeAsDate() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(arrivalTime);
		return date;
	}
}
