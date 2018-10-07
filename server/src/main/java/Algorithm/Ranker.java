package Algorithm;

import java.util.List;

import se.lth.base.server.data.*;

public class Ranker {
	private List<Ride> listOfRides;
	
	public Ranker(List<Ride> listOfRides) {
		this.listOfRides = listOfRides;
	}


	public List<Ride> rankByTime() {
		
	}
	
	public List<Ride> rankByDistance(){
		
	}
	
	private double distance(Location departureLocation, Location arrivalLocation) {
		
		double lat1 = departureLocation.getLatitude();
		double lon1 = departureLocation.getLongitude();
		double lat2 = arrivalLocation.getLatitude();
		double lon2 = arrivalLocation.getLongitude();
		
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		distance = Math.pow(distance, 2);

		return Math.sqrt(distance);
	}

}
