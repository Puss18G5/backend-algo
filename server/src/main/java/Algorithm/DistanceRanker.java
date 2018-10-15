package Algorithm;

import java.util.ArrayList;
import java.util.List;

import se.lth.base.server.data.Location;
import se.lth.base.server.data.Ride;

public class DistanceRanker {
	private Location userArrivalLocation;
	private List<Ride> rideAlternatives;

	/**
	 * 
	 * Class used for sorting Rides based on distance from the arrival locatione.
	 * Rides which has an arrival located further than 4 km from the arrival
	 * location searched for by the user, are excluded from the list.
	 * 
	 * @param departureTime, rideAlternatives
	 */
	public DistanceRanker(Location userArrivalLocation, List<Ride> rideAlternatives) {
		this.userArrivalLocation = userArrivalLocation;
		this.rideAlternatives = rideAlternatives;
	}

	public List<Ride> rank() {
		DistTuple[] temp = sortableDistanceList(userArrivalLocation, rideAlternatives);
		sort(temp, 0, temp.length - 1);
		ArrayList<Ride> result = new ArrayList<Ride>();

		for (DistTuple dt : temp) {
			result.add(dt.getRide());
		}

		return result;
	}

	/**
	 * Main function that sorts arr[l..r] using merge()
	 *
	 */
	private void sort(DistTuple[] rides, int l, int r) {
		if (l < r) {
			// Find the middle point
			int m = (l + r) / 2;

			// Sort first and second halves
			sort(rides, l, m);
			sort(rides, m + 1, r);

			// Merge the sorted halves
			merge(rides, l, m, r);
		}
	}

	/**
	 * Merges two subarrays of arr[]. First subarray is arr[l..m], second subarray
	 * is arr[m+1..r]
	 */
	private void merge(DistTuple[] rides, int l, int m, int r) {
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		// Create temp arrays
		DistTuple L[] = new DistTuple[n1];
		DistTuple R[] = new DistTuple[n2];

		// Copy data to temp arrays
		for (int i = 0; i < n1; ++i)
			L[i] = rides[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = rides[m + 1 + j];

		// Merge the temp arrays
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

		// Copy remaining elements of L[] if any
		while (i < n1) {
			rides[k] = L[i];
			i++;
			k++;
		}

		// Copy remaining elements of R[] if any
		while (j < n2) {
			rides[k] = R[j];
			j++;
			k++;
		}
	}

	/**
	 * Calculates distance between two points given the coordinates
	 * 
	 * @param locA
	 * @param locB
	 * @return double
	 */
	private double calculateDistance(Location locA, Location locB) {

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
		return Math.sqrt(distance);
	}

	/**
	 * Transfers an ArrayList to array[] for enabling sort()-method.
	 * 
	 **/
	private DistTuple[] sortableDistanceList(Location usersArrivalLocation, List<Ride> rideList) {
		List<DistTuple> temp = new ArrayList<DistTuple>();
		double dist = 0;

		for (Ride rd : rideList) {
			dist = calculateDistance(usersArrivalLocation, rd.getArrivalLocationAsLocation());

			// Exclude the Rides with at distance further than 4 km from the arrival
			// location
			if (dist < (double) 4000) {
				temp.add(new DistTuple(rd, dist));
			}
		}

		int i = 0;
		DistTuple[] dt = new DistTuple[temp.size()];
		for (DistTuple distTup : temp) {
			dt[i] = distTup;
			i++;
		}

		return dt;
	}

	/**
	 * Helper class which wraps one Ride and the distance between the users chosen
	 * destination location and the registered Rides destination location, to
	 * simplify the sorting process.
	 */
	private class DistTuple {
		private Ride rs;
		private double distance;

		private DistTuple(Ride rs, double distance) {
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
}
