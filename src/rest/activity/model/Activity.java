package rest.activity.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

import rest.activity.dao.ActivityDao;

/**
 * The persistent class for the "Activity" database table.
 * 
 */
@Entity
@Table(name = "Activity")
@NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a")
@XmlRootElement(name="activity")
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;

	// supported predefined activity types
	public static final String[] ActivityTypes = {
           "Sport",
           "Social",
           "University",
           "Extreme",
           "City",                                            
	};
	
	@Id
	@GeneratedValue
	@Column(name = "activityId")
	private int activityId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "place")
	private String place;
	
	@Column(name = "type")
	private String type;
	
//	@Temporal(TemporalType.DATE)
	@Column(name="startdate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private String startdate;
	
	@ManyToOne
	@JoinColumn(name="personId",referencedColumnName="personId")
	private Person person;

	public Activity() {
	}
	
	/**
	 * 
	 * @param activityType must be one of values from ActivityTypes array
	 * @param startdate start date in format dd-MM-yyyy
	 * @throws IllegalArgumentException
	 */
	public Activity(String name, 
			String description,
			String place, 
			String activityType, 
			String startdate) throws IllegalArgumentException {
		super();
		// validate activity type parameter 
		if ( !Arrays.asList(Activity.ActivityTypes).contains(activityType) )
			throw new IllegalArgumentException("Activity type " 
					+ activityType + " is not supported!\nList of available types:"
					+ ActivityTypes.toString());
		
		// validate startdate parameter
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
	    //To make strict date format validation
	    formatter.setLenient(false);
	    Date parsedDate = null;
	    try {
	        parsedDate = formatter.parse(startdate);
	    } catch (ParseException e) {
	    	throw new IllegalArgumentException("Start date does not match to format dd-MM-yyyy!");
	    }
	    
		this.name = name;
		this.description = description;
		this.place = place;
		this.type = activityType;
		this.startdate = startdate;
	}


	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int idActivity) {
		this.activityId = idActivity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getActivityType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public static Activity getActivityById(int activityId) {
		EntityManager em = ActivityDao.instance.createEntityManager();
		Activity p = em.find(Activity.class, activityId);
		ActivityDao.instance.closeConnections(em);
		return p;
	}
	

	public static List<Activity> getAll() {
		EntityManager em = ActivityDao.instance.createEntityManager();
	    List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class)
	    												.getResultList();
	    ActivityDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Activity saveActivity(Activity p) {
		EntityManager em = ActivityDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		ActivityDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Activity updateActivity(Activity a) {
		EntityManager em = ActivityDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		a=em.merge(a);
		tx.commit();
		ActivityDao.instance.closeConnections(em);
	    return a;
	}
	
	public static void removeActivity(Activity a) {
		EntityManager em = ActivityDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    a=em.merge(a);
	    em.remove(a);
	    tx.commit();
	    ActivityDao.instance.closeConnections(em);
	}
}

