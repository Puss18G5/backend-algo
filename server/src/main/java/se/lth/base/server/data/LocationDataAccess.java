package se.lth.base.server.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import se.lth.base.server.database.DataAccess;
import se.lth.base.server.database.Mapper;

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
    public List<Location> getAllFoo() {
        return query("SELECT * FROM locations");
    }

}
