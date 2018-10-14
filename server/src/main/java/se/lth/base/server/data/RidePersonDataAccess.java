package se.lth.base.server.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import se.lth.base.server.Config;
import se.lth.base.server.database.DataAccess;
import se.lth.base.server.database.Mapper;

public class RidePersonDataAccess extends DataAccess<RidePerson>{
	
	private static class RidePersonMapper implements Mapper<RidePerson> {
		@Override
		public RidePerson map(ResultSet resultSet) throws SQLException {
			return new RidePerson(resultSet.getInt("ride_id"), resultSet.getInt("user_id"), "Passenger");
		}
	}
	
	public RidePersonDataAccess(String driverUrl) {
		super(driverUrl, new RidePersonMapper());
	}
	
	
	/**
	 * 
	 * @param rideId
	 * @return passenger list 
	 */
	public List<RidePerson> getPassengerList(int rideId){
		List<RidePerson> passengers = query("SELECT * FROM ride_passengers WHERE ride_id = ?", rideId); 		
		return passengers;
	}
	
	public List<RidePerson> getRidesAsPassenger(int userId) {
		List<RidePerson> rides = query("SELECT * FROM ride_passengers WHERE user_id = ?", userId); 
		return rides;		
	}
	
	
	
}
