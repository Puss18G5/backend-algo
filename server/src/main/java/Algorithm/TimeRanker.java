package Algorithm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.lth.base.server.data.Ride;

public class TimeRanker {
	private List<Ride> rideAlternatives;

	public TimeRanker(List<Ride> rideAlternatives) {
		this.rideAlternatives = rideAlternatives;
	}

	/**/
	public List<Ride> rank() throws ParseException {
		Ride[] temp = sortableTimeList(rideAlternatives);
		sort(temp, 0, rideAlternatives.size() - 1);
		List<Ride> result = new ArrayList<Ride>();

		for (Ride rd : temp) {
			result.add(rd);
		}

		return result;
	}

	/* Main function that sorts arr[l..r] using merge() */
	private void sort(Ride[] arr, int l, int r) throws ParseException {
		if (l < r) {
			// Find the middle point
			int m = (l + r) / 2;

			// Sort first and second halves
			sort(arr, l, m);
			sort(arr, m + 1, r);

			// Merge the sorted halves
			merge(arr, l, m, r);
		}
	}

	private void merge(Ride arr[], int l, int m, int r) throws ParseException {
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		// Create temp arrays
		Ride L[] = new Ride[n1];
		Ride R[] = new Ride[n2];

		// Copy data to temp arrays 
		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarry array */
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i].departureTimeAsDate().before(R[j].departureTimeAsDate())) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		// Copy remaining elements of L[] if any */
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		//Copy remaining elements of R[] if any */
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	private Ride[] sortableTimeList(List<Ride> rideList) {
		Ride[] rd = new Ride[rideList.size()];
		int i = 0;
		for (Ride r : rideList) {
			rd[i] = r;
			i++;
		}
		return rd;
	}
}
