package se.lth.base.server.rest;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import se.lth.base.server.Config;
import se.lth.base.server.data.Ride;
import se.lth.base.server.data.RideDataAccess;
import se.lth.base.server.data.RidePerson;
import se.lth.base.server.data.RidePersonDataAccess;
import se.lth.base.server.data.User;

@Path("rideperson")
public class RidePersonResource {
    private final RideDataAccess rideDao = new RideDataAccess(Config.instance().getDatabaseDriver());
    private final RidePersonDataAccess ridePersonDao = new RidePersonDataAccess(Config.instance().getDatabaseDriver());
	private final User user;
	
	public RidePersonResource(@Context ContainerRequestContext context) {
		this.user = (User) context.getProperty(User.class.getSimpleName());
	}
	
	
	/**
	 * 
	 * @param rideId
	 * @return all travelers on a specified ride, both driver and passengers
	 */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @PermitAll
    @Path("{rideId}")
	public List<RidePerson> getAllTravelers(@PathParam("rideId") int rideId) {
    	Ride ride = rideDao.getRide(rideId);
    	List<RidePerson> ridesAsPassenger = ridePersonDao.getPassengerList(rideId);
    	ridesAsPassenger.add(new RidePerson(rideId, ride.getDriverId(), "Driver"));
		return ridesAsPassenger;
	}
    
    
    /**
     * 
     * @return all rides where the user is a passenger
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @PermitAll
    public List<RidePerson> getRidesAsPassenger() {
    	return ridePersonDao.getRidesAsPassenger(user.getId());
    }
    
    
}
