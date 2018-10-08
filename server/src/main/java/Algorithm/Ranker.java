
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
	
	public List<Ride> rankByDistance(Ride userRide){
		DistanceRanker dr = new DistanceRanker(userRide, listOfRides);
		return dr.rank();
	}
}