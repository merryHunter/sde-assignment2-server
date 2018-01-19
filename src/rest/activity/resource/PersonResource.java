package rest.activity.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import rest.activity.model.Activity;
import rest.activity.model.Person;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class PersonResource {
    @Context
    UriInfo uriInfo;
    
    @Context
    Request request;
    
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    private static final Logger logger =
            Logger.getLogger(PersonResource.class.getName());
    
    public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    // Request #2
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getPerson() {
        Person person = this.getPersonById(id);
        if (person == null) {
        	logger.info("person with id " + Integer.toString(id) + " not found!");
        	logger.info(Response.Status.NOT_FOUND.toString());
			 return Response.status(Response.Status.NOT_FOUND).build();
		}
        return Response.status(Response.Status.OK).entity(person).build();
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Person getPersonHTML() {
        Person person = this.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        System.out.println("Returning person... " + person.getIdPerson());
        return person;
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        logger.info("--> Updating Person... " +this.id);
        logger.info("--> " + person.toString());        
        Response res;
        Person existing = getPersonById(this.id);

        if (existing == null) {
            res = Response.noContent().build();
        } else {
        	//get activities of existing person
        	List<Activity> acts = existing.getActivityPreferences();
        	// set acts to updated info
            person.setIdPerson(this.id);
            person.setActivityPreferences(acts);
            Person.updatePerson(person);
            res = Response.ok(uriInfo.getAbsolutePath()).build();
        }
        return res;
    }

    @DELETE
    public void deletePerson() {
    	logger.info("deleting person with id " + Integer.toString(id));
        Person c = getPersonById(id);
        if (c == null)
            throw new RuntimeException("Delete: Person with " + id
                    + " not found");
        Person.removePerson(c);
    }

    public Person getPersonById(int personId) {
    	logger.info("Reading person from DB with id: "+personId);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        return Person.getPersonById(personId);
    }
    
    
    // Request #7
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}")
    public List<Activity> getActivitiesByType(@PathParam("activity_type") String type){
    	Person p = getPersonById(id);
    	List<Activity> acts = new ArrayList<Activity>();
    	for (Activity a: p.getActivityPreferences()) {
    		if (a.getType().equals(type)){
    			acts.add(a);
    		}
    	}
		return acts;
    }
    
    
    // Request #8
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}/{activity_id}")
    public List<Activity> getActivityByTypeId(@PathParam("activity_type") String type,
    										@PathParam("activity_id") int activity_id){
    	Person p = getPersonById(id);
    	List<Activity> acts = new ArrayList<Activity>();
    	for (Activity a: p.getActivityPreferences()) {
    		if (a.getType().equals(type) && a.getIdActivity() == activity_id){
    			acts.add(a);
    		}
    	}
		return acts;
    }
    
    //Request #9
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}")
    public List<Activity> addActivity(@PathParam("activity_type") String type, Activity activity){
    	Person p = getPersonById(id);
    	List<Activity> acts = p.getActivityPreferences();
    	logger.info("adding new activity..");
    	activity.setType(type);
    	Activity.saveActivity(activity);
    	acts.add(activity);
    	p.setActivityPreferences(acts);
    	Person.updatePerson(p);
		return acts;
    }
    
    // Request #10
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}/{activity_id}")
    public Response updateActivityType(@PathParam("activity_type") String activity_type, 
								@PathParam("activity_id") int activity_id, Activity activity) {
        logger.info("--> Updating Activity... " +this.id);
        Response res;
        Person existing = getPersonById(this.id);

        if (existing == null) {
            res = Response.noContent().build();
        } else {
        	boolean activity_exist = false;
        	//get activities of existing person
        	List<Activity> acts = existing.getActivityPreferences();
        	for (int i = 0; i < acts.size(); i++) {
        		Activity a = acts.get(i);
        		if (a.getIdActivity() == activity_id) {
        			activity_exist = true;
        			a.setDescription(activity.getDescription());
        			a.setName(activity.getName());
        			a.setPlace(activity.getPlace());
        			a.setStartdate(activity.getStartdate());
        			a.setType(activity.getType()); 	
        			a.setPerson(existing);
        		}
        		Activity.updateActivity(a);
        		Person.updatePerson(existing);
        	}
        	if ( !activity_exist) {
        		throw new RuntimeException("Update: Activity with id " + activity_id
                        + " not found");
        	}
            res = Response.ok(uriInfo.getAbsolutePath()).build();
        }
        return res;
    }
    
    // Request #11
    // Not implemented
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}?before={beforeDate}&after={afterDate} ")
    public List<Activity> getActivityWithinDates(@PathParam("activity_Type") String type, 
			@DefaultValue("")@QueryParam("before") String beforeDate, 
			@DefaultValue("")@QueryParam("after") String afterDate) {
    	throw new RuntimeException("Not implemented!");
    	
    }
}