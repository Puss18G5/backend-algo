package se.lth.base.server.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import se.lth.base.server.database.DataAccess;
import se.lth.base.server.database.Mapper;

/**
 * Access the locations stored in the database.
 * All locations are inserted to the table when the CreateSchema class is initiated.
 * @see DataAccess
 */
public class LocationDataAccess extends DataAccess<Location> {
	
    private static class LocationMapper implements Mapper<Location> {
    	
        @Override
        public Location map(ResultSet resultSet) throws SQLException {
            return new Location(resultSet.getString("location_name"),
                    resultSet.getFloat("latitude"),
                    resultSet.getFloat("longitude"));
        }
    }

    public LocationDataAccess(String driverUrl) {
        super(driverUrl, new LocationMapper());
    }
    
    /**
     * @return all locations in the locations table
     */
    public List<Location> getAllLocations() {
        return query("SELECT * FROM locations");
    }

}
