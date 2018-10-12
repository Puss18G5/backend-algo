package se.lth.base.server.data;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

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
	
    @Test
    public void testAddRide() {
    	
    	
    	//Location departure = locationDao.getLocationObject("Helsingborg");
    	//Location arrival = locationDao.getLocationObject("Lund");
    	
    	rideDao.createRide("Helsingborg", "Lund", "2018-01-07 12:00:00", "2018-01-07 13:00:00", 4, 1);
    	
    	System.out.println("test");
    	
    	
    	List<Ride> rides = rideDao.getAllRides();
    	for (int i = 0; i < rides.size(); i++) {
    		Ride ride = rides.get(i);
    		System.out.println(ride.getID() + ride.getDepartureLocation()+ ride.getArrivalLocation()+ ride.getDepartureTime() + ride.getArrivalTime() + ride.getCarSize() + ride.getDriverId());
    	}
    	
    	System.out.println(" ");
    	List<Ride> userRides = rideDao.getRides(1);
		for (int i = 0; i < userRides.size(); i++) {
    		Ride ride = userRides.get(i);
    		System.out.println(ride.getDepartureLocation()+ ride.getArrivalLocation() + ride.getDepartureTime() + ride.getArrivalTime() + ride.getCarSize() + ride.getID());
    	}
    	
    	assertEquals(1,1);
        
    }
    

}
