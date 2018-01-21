package rest.activity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement(name="activity_type")
public class ActivityTypeWrapperXML implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		@JsonFormat(shape = JsonFormat.Shape.STRING)
	    private String[] activity_type; // = (ArrayList<String>) Arrays.asList(Activity.ActivityTypes);
	    
	    public String[] getActivityType() {
	    	return activity_type;
	    }
	    
	    public void setActivityType(String[] t){
	    	this.activity_type = t;
	    }
	    
	    public ActivityTypeWrapperXML(){}
	    
	    public ActivityTypeWrapperXML(String[] a) {
	    	this.activity_type = a;
	    }
}
