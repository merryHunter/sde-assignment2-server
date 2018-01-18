package rest.activity.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import rest.activity.dao.ActivityDao;
import rest.util.ActivityUtil;

/**
 * The persistent class for the "Person" database table.
 * 
 */
@Entity  // indicates that this class is an entity to persist in DB
@Table(name="\"Person\"") // to what table must be persisted
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement(name="person")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue //(strategy=GenerationType.AUTO) 
//    @Column(name="personId") // maps the following attribute to a column
    private int personId;
    
    @Column(name="lastname")
    private String lastname;
    
    @Column(name="firstname")
    private String firstname;
    
    @Column(name="birthdate")
    private String birthdate; 
    
    @OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@XmlElementWrapper(name="preferences")
    private List<Activity> activitypreference;
   
    public Person() {}
    
    /**
     * 
     * @param birthdate Must be in format yyyy-MM-dd
     * @param activitypreferences
     */
    public Person(String firstname, 
    		String lastname, 
    		String birthdate,
    		List<Activity> activitypreferences) throws IllegalArgumentException{
		super();
		
		// validate birthdate param
		birthdate = ActivityUtil.validateDateString(birthdate);
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.activitypreference = activitypreferences;
    }
    
    // queries
    public List<Activity> getActivitiesWithType(String activityType) 
    		{return null;}
    
    public Activity addActivityWithType(String activityType, Activity activity) 
    		{return null;}
    
    public List<Activity> getActivitiesWithWithinRange(String activityType, 
    		String beforeDate, 
    		String afterDate) {return null;}
    
    
    public static Person getPersonById(int personId) {
        EntityManager em = ActivityDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        ActivityDao.instance.closeConnections(em);
        return p;
    }
    
    public static List<Person> getAll() {
        EntityManager em = ActivityDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        ActivityDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = ActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        ActivityDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = ActivityDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        ActivityDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = ActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        ActivityDao.instance.closeConnections(em);
    }
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int idPerson) {
		this.personId = idPerson;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	@XmlTransient
	public List<Activity> getActivityPreferences() {
	    return activitypreference;
    }
    
    public void setActivityPreferences(List<Activity> activities) {
	    this.activitypreference = activities;
    }
    
}