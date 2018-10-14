package se.lth.base.server.rest;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.lth.base.server.Config;
import se.lth.base.server.data.LocationDataAccess;
import se.lth.base.server.data.Ride;
import se.lth.base.server.data.RideDataAccess;
import se.lth.base.server.data.RidePerson;
import se.lth.base.server.data.RidePersonDataAccess;
import se.lth.base.server.data.Role;
import se.lth.base.server.data.User;

@Path("rideperson")
public class RidePersonResource {
    private final RideDataAccess rideDao = new RideDataAccess(Config.instance().getDatabaseDriver());
    private final RidePersonDataAccess ridePersonDao = new RidePersonDataAccess(Config.instance().getDatabaseDriver());
	private final User user;
	
	public RidePersonResource(@Context ContainerRequestContext context) {
		this.user = (User) context.getProperty(User.class.getSimpleName());
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @PermitAll
    @Path("{rideId}")
	public List<RidePerson> getPassengers(@PathParam("rideId") int rideId) {
		return ridePersonDao.getPassengerList(rideId);
	}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @PermitAll
    public List<RidePerson> getRides() {
    	return ridePersonDao.getRides(user.getId());
    }
}
