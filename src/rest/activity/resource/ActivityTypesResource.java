package rest.activity.resource;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.activity.model.Activity;

@Stateless
@LocalBean
@Path("/activity_types")
public class ActivityTypesResource {
	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	
	// Request #6
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String[] getAllActivityTypes() {
		return Activity.ActivityTypes;
	}
}
