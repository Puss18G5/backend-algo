package se.lth.base.server.data;
import org.junit.Test;
import se.lth.base.server.Config;
import se.lth.base.server.database.BaseDataAccessTest;
import se.lth.base.server.database.DataAccessException;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Date;

/**
 * 
 * @author Isabella Gagner
 * Class test, Ride class
 */

public class RideTest {

	private Ride ride = new Ride(0, "Stockholm", "Malmö",
			"2018/10/23 19:00:00", "2018/10/23 20:00:00", 3, 2);
	
	@Test
	public void getArrivalLocation() {
		assertEquals(ride.getArrivalLocation(), "Malmö");
	}
	
	@Test
	public void getDepartureLocation() {
		assertEquals(ride.getDepartureLocation(), "Stockholm");
	}
	
	@Test
	public void getCarSize() {
		assertEquals(ride.getCarSize(), 3);
	}
	
	@Test
	public void getID() {
		assertEquals(ride.getID(), 1);
	}
	
	@Test
	public void addTraveler() {
		User u = new User(1,Role.USER,"email", "name");
		assertTrue(ride.addTraveler(u));
		assertEquals(ride.getTravelerList().size(),1);
	}
	
	@Test
	public void getTravelerList() {
		User u = new User(1,Role.USER,"email", "name");
		ride.addTraveler(u);
		assertNotNull(ride.getTravelerList());
	}
	
	@Test
	public void addRemoveTraveler() {
		User u = new User(1,Role.USER,"email", "name");
		assertTrue(ride.addTraveler(u));
		assertTrue(ride.removeTraveler(u));
		assertEquals(ride.getTravelerList().size(),0);
	}

	@Test
	public void tryGetTime() {
		assertNotNull(ride.getArrivalTime());
		assertNotNull(ride.getDepartureTime());
//		System.out.println(ride.getArrivalTime().toString());
//		System.out.println(ride.getDepartureTime().toString());
	}
	
	@Test
	public void tryGetTimeAsDate() throws ParseException {
		Date aDate = ride.arrivalTimeAsDate();
		Date dDate = ride.departureTimeAsDate();
		System.out.println(aDate.toString());
		System.out.println(dDate.toString());
	}
	
}
