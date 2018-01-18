package rest.activity.model;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonFormat;

import rest.activity.dao.ActivityDao;
import rest.util.ActivityUtil;

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

	@XmlTransient
	public static final String[] ActivityTypes = {
		"Sport",
		"Social",
		"University",
		"City",
		"Extreme"
	};
	
	@Id
	@GeneratedValue
	@Column(name = "idActivity")
	private int idActivity;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "place")
	private String place;
	
	@Column(name = "type")
	private String type;
	
	@Column(name="startdate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private String startdate;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public Activity() {
	}
	
	/**
	 * 
	 * @param type One of values in array ActivityTypes
	 * @param startdate String representing a date in format yyyy-MM-dd
	 * @throws IllegalArgumentException if parameters above are not valid
	 */
	public Activity(String name, 
			String description,
			String place, 
			String type, 
			String startdate) throws IllegalArgumentException {
		super();
		
		if ( !Arrays.asList(Activity.ActivityTypes).contains(type)) {
			throw new IllegalArgumentException("The activity type "
					+ type + " is not supported!\nAvailable: "
					+ Activity.ActivityTypes.toString());
		}
		
		this.startdate = ActivityUtil.validateDateString(startdate);
		
		this.name = name;
		this.description = description;
		this.place = place;
		this.type = type;
		
	}


	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
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

	public String getType() {
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
	    List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
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
