package rest.activity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement(name="activityTypeWrapperXML")
public class ActivityTypeWrapperXML implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING)
	    private String[] activityTypes; // = (ArrayList<String>) Arrays.asList(Activity.ActivityTypes);
	    
	    public String[] getActivityTypes() {
	    	return activityTypes;
	    }
	    
	    public void setActivityTypes(String[] t){
	    	this.activityTypes = t;
	    }
	    
	    public ActivityTypeWrapperXML(){}
	    
	    public ActivityTypeWrapperXML(String[] a) {
	    	this.activityTypes = a;
	    }
}
