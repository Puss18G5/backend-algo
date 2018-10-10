package se.lth.base.server.data;

import static junit.framework.TestCase.assertTrue;

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
    
	

}
