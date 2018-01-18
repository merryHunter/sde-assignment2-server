package rest.activity.test;

import static org.junit.Assert.*;

import rest.activity.model.Activity;
import rest.activity.model.Person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;   
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonTest {

    @Test
    public void readPersonListTest() {
        System.out.println("--> TEST: readPersonList");
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
                .getResultList();
        for (Person person : list) {
            System.out.println("--> Person = "+person.toString());
        }
        assertTrue(list.size()>0);
    }

    @Test
    public void addPersonWithDaoTest() {
        System.out.println("--> TEST: addPersonWithDao");

        List<Person> list = Person.getAll();
        int personOriginalCount = list.size();

        Person p = new Person();
        p.setFirstname("Pinco");
        p.setLastname("Pallino");
        Calendar c = Calendar.getInstance();
        c.set(1984, 6, 21);
        p.setBirthdate("1994-01-01");
        List<Activity> activities = new ArrayList<Activity>();
		// create activities
		try {
			activities.add(new Activity("Snowboarding", "Snowboarding in Alpines",
					"Dolimities, Italy", "Sport", "2017-01-01"));
			
			activities.add(new Activity("Skiing", "Skiing in Carpathian mountains",
					"Carpathy, Ukraine", "Sport", "2017-02-01"));
		}
		catch (IllegalArgumentException e){
			
		}
//		p.setActivityPreferences(activities);
		Person p1 = new Person("Ivan", "Chernukha", "1995-07-03", activities);
		activities.get(0).setPerson(p1);
		activities.get(1).setPerson(p1);
		
		System.out.println("--> TEST: addPersonWithDao ==> persisting person");
        Person.savePerson(p1);
        assertNotNull("Id should not be null", p1.getIdPerson());
        Person.savePerson(p);
        assertNotNull("Id should not be null", p.getIdPerson());
        System.out.println("--> TEST: addPersonWithDao ==> getting the list");
        list = Person.getAll();
        assertEquals("Table has two entities", personOriginalCount+1, list.size());

        Person newPerson = Person.getPersonById(p.getIdPerson());

        System.out.println("--> TEST: addPersonWithDao ==> removing new person");
//        Person.removePerson(newPerson);
        list = Person.getAll();
//        assertEquals("Table has two entities", personOriginalCount, list.size());
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Testing JPA on lifecoach database using 'introsde-jpa' persistence unit");
        emf = Persistence.createEntityManagerFactory("assignment2-server");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void afterClass() {
        em.close();
        emf.close();
    }

    @Before
    public void before() {
        tx = em.getTransaction();
    }

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private EntityTransaction tx;

}