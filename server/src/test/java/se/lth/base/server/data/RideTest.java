package se.lth.base.server.data;
import org.junit.Test;
import se.lth.base.server.Config;
import se.lth.base.server.database.BaseDataAccessTest;
import se.lth.base.server.database.DataAccessException;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author Isabella Gagner
 * Class test, Ride class
 */

public class RideTest {

	private Ride ride = new Ride(1, new Location("Stockholm", 59.3293, 18.0686), new Location("Malmö", 55.6050, 13.0038),
			new Date(), new Date(), 3);
	
	@Test
	public void getArrivalLocation() {
		assertEquals(ride.getArrivalLocation().getLocation(), "Malmö");
	}
	
	@Test
	public void getDepartureLocation() {
		assertEquals(ride.getDepartureLocation().getLocation(), "Stockholm");
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
		User u = new User(1,"email",Role.USER, "name");
		assertTrue(ride.addTraveler(u));
		assertEquals(ride.getTravelerList().size(),1);
	}
	
	@Test
	public void getTravelerList() {
		User u = new User(1,"email",Role.USER, "name");
		ride.addTraveler(u);
		assertNotNull(ride.getTravelerList());
	}
	
	@Test
	public void addRemoveTraveler() {
		User u = new User(1,"email",Role.USER, "name");
		assertTrue(ride.addTraveler(u));
		assertTrue(ride.removeTraveler(u));
		assertEquals(ride.getTravelerList().size(),0);
	}
	
	//TODO
	//add tests regarding date
	
	
}
