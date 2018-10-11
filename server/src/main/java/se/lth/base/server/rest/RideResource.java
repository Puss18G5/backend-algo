package se.lth.base.server.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import se.lth.base.server.Config;
import se.lth.base.server.data.Ride;
import se.lth.base.server.data.RideDataAccess;
import se.lth.base.server.data.User;

@Path("ride")
public class RideResource {
    private final RideDataAccess rideDao = new RideDataAccess(Config.instance().getDatabaseDriver());
    private final User user;
    
    public RideResource(@Context ContainerRequestContext context) {
        this.user = (User) context.getProperty(User.class.getSimpleName());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @PermitAll
    public Ride createRide(Ride ride) throws URISyntaxException {
    	return rideDao.createRide(ride.getDepartureLocation(), ride.getArrivalLocation(), ride.getDepartureTime(), ride.getArrivalTime(), ride.getCarSize(), ride.getID());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("all")
    public List<Ride> getAllRides(){
    	return rideDao.getAllRides();    
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("user/{userId}")   
    public List<Ride> getRides(@PathParam("userId") int userId) {
    	return rideDao.getRides(userId);
    }
    
    
    public List<Ride> searchRelevantRides(String arrivalLocation, String departureLocation, 
    		String arrivalTime, String departureTime){
    	return rideDao.getAllRelevantRides(arrivalLocation,departureLocation,arrivalTime,departureTime);
    }
    
}
