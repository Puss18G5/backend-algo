package se.lth.base.server.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import Algorithm.Ranker;
import se.lth.base.server.Config;
import se.lth.base.server.database.DataAccess;
import se.lth.base.server.database.Mapper;

/**
 * Contains data access methods for rides
 * @author Sani Mesic
 * 
 * 
 * TODO oklart om Location kan användas som parameter då koordinaterna är i en annan table
 * 
 */
public class RideDataAccess extends DataAccess<Ride> {

	private final LocationDataAccess locationDao = new LocationDataAccess(Config.instance().getDatabaseDriver());
	
	private static class RideMapper implements Mapper<Ride> {
		@Override
		public Ride map(ResultSet resultSet) throws SQLException {
			return new Ride(resultSet.getString("departure_location"),
					resultSet.getString("destination"),
					resultSet.getString("departure_time"), 
					resultSet.getString("arrival_time"),
					resultSet.getInt("nbr_seats"),
					resultSet.getInt("driver_id"));
		}
	}

	public RideDataAccess(String driverUrl) {
		super(driverUrl, new RideMapper());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param departureLocation
	 * @param destination
	 * @param departureTime
	 * @param arrivalTime
	 * @param nbrSeats
	 * @param driverId
	 * @return new ride containing the input parameters
	 */
	public Ride createRide(String departureLocation, String destination, String departureTime, String arrivalTime, int nbrSeats, int driverId) {
		int rideId = insert("INSERT INTO rides (departure_time, arrival_time, nbr_seats, driver_id, departure_location, destination) VALUES (?,?,?,?,?,?)",
				        departureTime, arrivalTime, nbrSeats, driverId, departureLocation, destination);
		return new Ride(departureLocation, destination, departureTime, arrivalTime, nbrSeats, driverId);
	}
	
	/**
	 * 
	 * @return all rides currently in the system
	 */
	public List<Ride> getAllRides(){
		return query("SELECT * FROM rides JOIN locations ON rides.departure_location = locations.location_name");
	}
	
	/**
	 * @param userId
	 * @return all rides connected to one user id, i.e. where the specific user is either a passenger or driver
	 *
	 */
	public List<Ride> getRides(int userId){
		return query("SELECT * FROM ride_passengers JOIN rides ON ride_passengers.user_id = rides.driver_id JOIN locations ON rides.departure_location = locations.location_name WHERE user_id = ?", userId);
	}
	

	/**
	 * 
	 * @param aLocation
	 * @param dLocation
	 * @param arrivalTime
	 * @param departureTime
	 * @return
	 * @throws ParseException
	 */
	public List<Ride> getRelevantRides(String aLocation, String dLocation,
    		String arrivalTime, String departureTime) throws ParseException {
		Location arrivalLocation = locationDao.getLocationObject(aLocation);
		Location departureLocation = locationDao.getLocationObject(dLocation);
		Ride userRide = new Ride(departureLocation, arrivalLocation, departureTime, arrivalTime);
		List<Ride> allRides = getAllRides();
		Ranker ranker = new Ranker(allRides, userRide);
		return ranker.rank();
	}


}
