package se.lth.base.server.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

	private static class RideMapper implements Mapper<Ride> {
		@Override
		public Ride map(ResultSet resultSet) throws SQLException {
			return new Ride(resultSet.getInt("ride_id"),
					new Location(resultSet.getString("departure_location"), resultSet.getDouble("latitude"),
							resultSet.getDouble("longitude")),
					new Location(resultSet.getString("destination"), resultSet.getDouble("latitude"),
							resultSet.getDouble("longitude")),
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
	public Ride createRide(Location departureLocation, Location destination, String departureTime, String arrivalTime, int nbrSeats, int driverId) {
		int rideId = insert("INSERT INTO rides (departure_time, arrival_time, nbr_seats, driver_id, departure_location, destination) VALUES (?,?,?,?,?,?)",
				        departureTime, arrivalTime, nbrSeats, driverId, departureLocation.getLocation(), destination.getLocation());
		return new Ride(rideId, departureLocation, destination, departureTime, arrivalTime, nbrSeats, driverId);
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
	 *
	 * DOESNT WORK YET
	 */
	public List<Ride> getRides(int userId){
		return query("SELECT * FROM rides LEFT JOIN ride_passengers USING (ride_id) LEFT JOIN users USING (user_id) WHERE user_id = ?", userId);
	}
	
	
	

}
