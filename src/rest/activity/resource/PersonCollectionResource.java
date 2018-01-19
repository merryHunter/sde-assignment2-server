package rest.activity.resource;

import rest.activity.model.Person;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonCollectionResource {

	// Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="assignment2-server-unit")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "assignment2-server-unit",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;

    private static final Logger logger =
            Logger.getLogger(PersonCollectionResource.class.getName());
    
    
    // Return the list of people to the user in the browser
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<Person> getPersonsBrowser() {
        logger.info("Getting list of people...");
        List<Person> people = Person.getAll();
        return people;
    }

    // returns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
    	logger.info("Getting count...");
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Person newPerson(Person person) throws IOException {
    	logger.info("Creating new person...");            
        return Person.savePerson(person);
    }

    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonResources
    // Allows to type http://localhost:599/base_url/1
    // 1 will be treaded as parameter todo and passed to PersonResource
    @Path("{personId}")
    public PersonResource getPerson(@PathParam("personId") int id) {
        return new PersonResource(uriInfo, request, id);
    }

}
