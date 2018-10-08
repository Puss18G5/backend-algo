package Algorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.lth.base.server.data.Location;
import se.lth.base.server.data.Ride;

public class DistanceRanker {
	private Ride userArrivalLocation;
	private List<Ride> rideAlternatives;

	public DistanceRanker(Ride userArrivalLocation, List<Ride> rideAlternatives) {
		this.userArrivalLocation = userArrivalLocation;
		this.rideAlternatives = rideAlternatives;
	}
	
	public List<Ride> rank(){
		DistTouple[] temp = sortableDistanceList(userArrivalLocation.getArrivalLocation(), rideAlternatives);
		sort(temp, 0, rideAlternatives.size() - 1);
		ArrayList<Ride> result = new ArrayList();
		
		for(DistTouple dt : temp){
			result.add(dt.getRide());
		}
		
		return result;
	}

	// Main function that sorts arr[l..r] using
	// merge()
	private void sort(DistTouple[] rides, int l, int r) {
		if (l < r) {
			// Find the middle point
			int m = (l + r) / 2;

			// Sort first and second halves
			sort(rides, l, m);
			sort(rides, m + 1, r);

			// Merge the sorted halves
			distanceMerge(rides, l, m, r);
		}
	}

	// Merges two subarrays of arr[].
	// First subarray is arr[l..m]
	// Second subarray is arr[m+1..r]
	private void distanceMerge(DistTouple[] rides, int l, int m, int r) {
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		/* Create temp arrays */
		DistTouple L[] = new DistTouple[n1];
		DistTouple R[] = new DistTouple[n2];

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = rides[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = rides[m + 1 + j];

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarry array
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i].getDistance() <= R[j].getDistance()) {
				rides[k] = L[i];
				i++;
			} else {
				rides[k] = R[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			rides[k] = L[i];
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */
		while (j < n2) {
			rides[k] = R[j];
			j++;
			k++;
		}
	}
	
	private double distance(Location locA, Location locB) {
		
		double lat1 = locA.getLatitude();
		double lon1 = locA.getLongitude();
		double lat2 = locB.getLatitude();
		double lon2 = locB.getLongitude();
		
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		distance = Math.pow(distance, 2);
		System.out.println(locA.getLocation() + " " + locB.getLocation() + " " + Math.sqrt(distance));
		return Math.sqrt(distance);
	}
	
	
	private DistTouple[] sortableDistanceList(Location usersArrivalLocation, List<Ride> rideList){
		DistTouple[] dt = new DistTouple[rideList.size()];
		int i = 0;
		for (Ride r: rideList) {
			dt[i] = new DistTouple(r, distance(usersArrivalLocation, r.getArrivalLocation()));
			i++;
		}
		return dt;
	}
	
	/*Helper class to wrap one Ride and the distance between the users shoosen destination location and the registered
	 * Rides destination location to simplify the sorting process*/
	private class DistTouple {
		private Ride rs;
		private double distance;

		private DistTouple(Ride rs, double distance) {
			this.rs = rs;
			this.distance = distance;
		}
		
		private Ride getRide() {
			return rs;
		}
		
		private double getDistance() {
			return distance;
		}
	}
	
	/*For test purposes only, should be removed after phase 3*/
	public static void main(String[] args) {
		//int id, Location departureLocation, Location arrivalLocation, Date aD, Date dD, int size
		Ride userRide = new Ride(1, new Location("Stockholm", (double) 59.329323, (double) 18.068581),
				new Location("Göteborg", 57.708870, 11.974560), new Date(), new Date(), 4);
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
		
		DistanceRanker dr = new DistanceRanker(userRide, rides);
		List<Ride> result = dr.rank();
		for (Ride r : result) {
			System.out.println(r.getDepartureLocation().getLocation() + " " + r.getArrivalLocation().getLocation());
		}
		
	}
}


