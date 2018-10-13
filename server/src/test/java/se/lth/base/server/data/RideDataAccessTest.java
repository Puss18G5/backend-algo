package se.lth.base.server.data;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import se.lth.base.server.Config;
import se.lth.base.server.database.BaseDataAccessTest;


/**
 * 
 * @author sanimesic
 * TODO fix tests
 */
public class RideDataAccessTest extends BaseDataAccessTest {
    
	private final RideDataAccess rideDao = new RideDataAccess(Config.instance().getDatabaseDriver());
    private final LocationDataAccess locationDao = new LocationDataAccess(Config.instance().getDatabaseDriver());
    private final UserDataAccess userDao = new UserDataAccess(Config.instance().getDatabaseDriver());

    @Test
    public void testAddRide() throws ParseException {
    	
    	rideDao.createRide("Helsingborg", "Lund", "2018-01-07 12:00:00", "2018-01-07 13:00:00", 4, 1);

    	List<Ride> rides = rideDao.getAllRides();
    	for (int i = 0; i < rides.size(); i++) {
    		Ride ride = rides.get(i);
    		System.out.println(ride.getID() + " " + ride.getCarSize());
    		//System.out.println(ride.getID() + ride.getDepartureLocation()+ ride.getArrivalLocation()+ ride.getDepartureTime() + ride.getArrivalTime() + ride.getCarSize() + ride.getDriverId());
    	}
    	
    	System.out.println(rideDao.checkIfEmptySeats(4));
    	
    	
    	rideDao.addUserToRide(4, 1);
    	
    	/**
    	System.out.println(" ");
    	List<Ride> userRides = rideDao.getRides(1);
		for (int i = 0; i < userRides.size(); i++) {
    		Ride ride = userRides.get(i);
    		System.out.println(ride.getID() + " " + ride.getCarSize());
    		
    		//System.out.println(ride.getID() + ride.getDepartureLocation()+ ride.getArrivalLocation() + ride.getDepartureTime() + ride.getArrivalTime() + ride.getCarSize() + ride.getID());
    	}
    	*/
		
    	rides = rideDao.getAllRides();
    	for (int i = 0; i < rides.size(); i++) {
    		Ride ride = rides.get(i);
    		System.out.println(ride.getID() + " " + ride.getCarSize());
    		//System.out.println(ride.getID() + ride.getDepartureLocation()+ ride.getArrivalLocation()+ ride.getDepartureTime() + ride.getArrivalTime() + ride.getCarSize() + ride.getDriverId());
    	}
		
    	
    	assertEquals(1,1);
        
    }
    

}
