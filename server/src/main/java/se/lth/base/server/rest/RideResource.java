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
    	return rideDao.createRide(ride.getDepartureLocation(), ride.getArrivalLocation(), 
    			ride.getDepartureTime(), ride.getArrivalTime(), ride.getCarSize(), ride.getDriverId());
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
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("join")
    public boolean joinRide(int rideId, int userId) z{
    	return rideDao.addUserToRide(rideId, userId);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path ("/{aLocation}/{dLocation}/{dTime}/{aTime}")
    public List<Ride> searchRelevantRides(	@PathParam("aLocation") String arrivalLocation,
    										@PathParam("dLocation") String departureLocation,
    										@PathParam("dTime") String departureTime,
    										@PathParam("aTime") String arrivalTime) throws ParseException{

    	return rideDao.getRelevantRides(arrivalLocation, departureLocation, arrivalTime, departureTime);
    }
    
}
