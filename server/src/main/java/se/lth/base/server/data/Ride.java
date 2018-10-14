package se.lth.base.server.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.lth.base.server.Config;

public class Ride {
	private String departureLocation;
	private String arrivalLocation;
	private List<User> allTravelers;
	private String arrivalTime;
	private String departureTime;
	private int carSize;
	private int id;
	private int driverId;
	private String role;
	private final LocationDataAccess locationDao = new LocationDataAccess(Config.instance().getDatabaseDriver());
	
	/**
	 * Constructor for rides made from front end
	 * @param rideId
	 * @param departureLocation
	 * @param arrivalLocation
	 * @param departureTime
	 * @param arrivalTime
	 * @param size
	 * @param driverId
	 */
	public Ride(int rideId, String departureLocation, String arrivalLocation, 
			String departureTime, String arrivalTime, int size, int driverId, String role) {
		this.id = rideId;
		this.arrivalLocation = arrivalLocation;
		this.departureLocation = departureLocation;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.carSize = size;
		this.allTravelers = new ArrayList<User>();
		this.driverId = driverId;
		this.role = role;
	}
	
	/**
	 * Constructor for rides made for Ranker
	 * @param departureLocation
	 * @param arrivalLocation
	 * @param departureTime
	 * @param arrivalTime
	 */
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
	
	public Location getArrivalLocationAsLocation() {
		return locationDao.getLocationObject(arrivalLocation);
	}
	
	public Location getDepartureLocationAsLocation() {
		return locationDao.getLocationObject(departureLocation);
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String newRole) {
		this.role = newRole;
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
