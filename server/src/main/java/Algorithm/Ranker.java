
package Algorithm;

import java.text.ParseException;
import java.util.List;

import se.lth.base.server.data.*;

public class Ranker {
	private List<Ride> listOfRides;
	private Ride userRide;
	private int userId;

	public Ranker(List<Ride> listOfRides, Ride userRide, int userId) {
		this.listOfRides = listOfRides;
		this.userRide = userRide;
		this.userId = userId;
	}

	/**
	 * 
	 * Ranks the Rides first depending on how close in time users desired departure time matches
	 * the Rides departure time. Thereafter by the distance between the users desired
	 * arrival location as specified in the userRide, and the arrival location of the
	 * Ride.
	 * Rides that the user has joined from before will be removed from the set of Rides.
	 * 
	 * @return List<Ride>
	 */
	public List<Ride> rank() throws ParseException {
		List<Ride> temp = listOfRides;
		return removeRidesAlreadyJoined(rankByDistance(rankByTime(userRide, temp)));
	}
	
	/**
	 * 
	 * @param tRides
	 * @return List<Ride>
	 */
	private List<Ride> rankByDistance(List<Ride> tRides) {
		DistanceRanker dr = new DistanceRanker(userRide.getArrivalLocationAsLocation(), tRides);
		return dr.rank();
	}

	/**
	 * 
	 * @param dRides
	 * @return List<Ride>
	 * @throws ParseException
	 */
	private List<Ride> rankByTime(Ride uRide, List<Ride> dRides) throws ParseException {
		TimeRanker tr = new TimeRanker(userRide.departureTimeAsDate(), dRides);
		return tr.rank();
	}

	/**
	 * Sorts out rides that the user has already joined. NOT CONFIRMED IF IT IS WORKING YET
	 * 
	 * @param listOfRides
	 */
	private List<Ride> removeRidesAlreadyJoined(List<Ride> listOfRides) {
		for(Ride r : listOfRides) {
			List<User> listOfTravelers = r.getTravelerList();
			for(User u : listOfTravelers) {
				if(u.getId() == userId ) {
					listOfRides.remove(r);
				}
			}
		}
		return listOfRides;
	}
}

