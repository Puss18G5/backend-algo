package se.lth.base.server.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import se.lth.base.server.Config;
import se.lth.base.server.data.Location;
import se.lth.base.server.data.LocationDataAccess;
import se.lth.base.server.data.Role;
import se.lth.base.server.data.User;

@Path("location")
public class LocationResource {
	
    private final LocationDataAccess locationDao = new LocationDataAccess(Config.instance().getDatabaseDriver());
    private final User user;

    public LocationResource(@Context ContainerRequestContext context) {
        this.user = (User) context.getProperty(User.class.getSimpleName());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @PermitAll
    @Path("all")
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

}
