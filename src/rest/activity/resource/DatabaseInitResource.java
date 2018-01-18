package rest.activity.resource;



import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

//import rest.activity.resource.DatabaseInit;
import rest.activity.model.Activity;
import rest.activity.model.Person;

@Stateless
@LocalBean
@Path("/database_init")
public class DatabaseInitResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Request#0
	
	private List<Person> getFiveNewPeople() {
		List<Activity> activities = new ArrayList<Activity>();
		List<Person> people = new ArrayList<Person>();
		Person p;
		try {
			activities.add(new Activity("Snowboarding", "Snowboarding in Alpines",
					"Dolimities, Italy", "Sport", "2017-01-01"));
			
			activities.add(new Activity("Skiing", "Skiing in Carpathian mountains",
					"Carpathy, Ukraine", "Sport", "2017-02-01"));
			
			activities.add(new Activity("Resort", "Resort on Bali island",
					"Bali, Indonesia", "Social", "2015-07-14"));
			
			activities.add(new Activity("Quest", "Quest in the city center",
					"Trento", "City", "2017-02-03"));
			
			activities.add(new Activity("Exhibition", "A new exhibition of a great painter",
					"Buonconsiglio Castle, Trento", "City", "2017-01-01"));
			
			activities.add(new Activity("Bungee jumping", "Bungee jumping from a high building in New York",
					"New York", "Extreme", "2018-01-18"));
			
			activities.add(new Activity("Escape room", "Escape from Palazzo delle Albere",
					"Palazzo delle Albere, Trento", "Social", "2017-01-01"));
			
			activities.add(new Activity("Studying", "Studying concurrency for the 2nd apello",
					"UniTrento", "University", "2017-01-22"));
			
			activities.add(new Activity("Hunting", "Hunting in forest for boars and foxes",
					"Koriukivka, Ukraine", "Extreme", "2012-11-10"));
		
			Person p1 = new Person("Ivan", "Chernukha", "1995-07-03", activities.subList(7, 9));
			people.add(p1);
			activities.get(7).setPerson(p1);
			activities.get(8).setPerson(p1);
			
			Person p2 = new Person("Oleg", "Matios", "1990-09-30", activities.subList(5, 7));
			people.add(p2);
			activities.get(5).setPerson(p2);
			activities.get(6).setPerson(p2);
			
			Person p3 = new Person("Nicol", "Vecchia", "1998-04-22", activities.subList(4, 5));
			people.add(p3);
			activities.get(4).setPerson(p3);
			
			Person p4 = new Person("Sofia", "Alzetta", "1985-05-05", activities.subList(2, 4));
			people.add(p4);
			activities.get(2).setPerson(p4);
			activities.get(3).setPerson(p4);
			
			Person p5 = new Person("Dmytro", "Koval", "1994-12-01", activities.subList(0, 2));
			people.add(p5);
			activities.get(0).setPerson(p5);	
			activities.get(1).setPerson(p5);

				
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return people;
	}
	
	
	// Request#1
		@GET
		@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
		public List<Person> getInitialPersonsList() {
			List<Person> newPeople = getFiveNewPeople();
			List<Person> people = new ArrayList<Person>();
			Person person;
			for (int i =0; i < 5; i++) {
				try {
					System.out.println("before save person");
					person = Person.savePerson(newPeople.get(i));
					people.add(person);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("The database has been populated with records of "
						+ Integer.toString(people.size())+ " people..");
			return people;
		}
	
	
	
	
	
}
