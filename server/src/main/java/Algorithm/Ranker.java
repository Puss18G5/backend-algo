
package Algorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.lth.base.server.data.*;

public class Ranker {
	private List<Ride> listOfRides;
	private Ride userRide;

	public Ranker(List<Ride> listOfRides, Ride userRide) {
		this.listOfRides = listOfRides;
		this.userRide = userRide;
	}

	/*
	 * Ranks the Rides first depending on the distance between the users desired
	 * arrival location as specified in the userRide and the arrival location of the
	 * Ride. Thereafter by how close in time users desired departure time matches
	 * the Rides departure time.
	 */
	public List<Ride> rank() {
		List<Ride> temp = listOfRides;
		return rankByTime(rankByDistance(temp));
	}

	// should be private?
	public List<Ride> rankByTime(List<Ride> dRides) {
		TimeRanker tr = new TimeRanker(dRides);
		return tr.rank();

	}

	// should be private?
	public List<Ride> rankByDistance(List<Ride> tRides) {
		DistanceRanker dr = new DistanceRanker(userRide.getArrivalLocation(), tRides);
		return dr.rank();
	}

	/* For test purposes only, should be removed after phase 3 */
	public static void main(String[] args) {
		Location userArrLoc = new Location("Göteborg", 57.708870, 11.974560);

		Ride r1 = new Ride(2, new Location("Stockholm", (double) 59.329323, (double) 18.068581),
				new Location("Malmö", 55.604980, 13.003822), new Date(), new Date(), 4);
		Ride r2 = new Ride(3, new Location("Stockholm", (double) 59.329323, (double) 18.068581),
				new Location("Märsta", 59.619842, 17.857571), new Date(), new Date(), 4);
		Ride r3 = new Ride(4, new Location("Stockholm", (double) 59.329323, (double) 18.068581),
				new Location("Trollhättan", 58.291553, 12.286609), new Date(), new Date(), 4);
		Ride r4 = new Ride(4, new Location("Stockholm", (double) 59.329323, (double) 18.068581),
				new Location("Kungälv", 57.872428, 11.975062), new Date(), new Date(), 4);

		ArrayList<Ride> rides = new ArrayList<Ride>();
		rides.add(r1);
		rides.add(r2);
		rides.add(r3);
		rides.add(r4);

		DistanceRanker dr = new DistanceRanker(userArrLoc, rides);
		List<Ride> result = dr.rank();
		for (Ride r : result) {
			System.out.println(r.getArrivalLocation().getLocation());
		}
	}
}