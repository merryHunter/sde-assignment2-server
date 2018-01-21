package rest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityUtil {

	/**
	 * Validate input parameter and if it's empty - set to default.
	 * @param date date as string, maybe containing bad formatted date
	 * @return default date or throw an IllegalArgumentException.
	 */
	public static String validateDateString(String date)
			throws IllegalArgumentException{
		if (date.isEmpty()){
			date = "01-01-2000";
		}
		
		// validate date parameter
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
	    //To make strict date format validation
	    formatter.setLenient(false);
	    Date parsedDate = null;
	    try {
	        parsedDate = formatter.parse(date);
	    }catch (ParseException e) {
	    	throw new IllegalArgumentException("The date " + date 
	    				+ " does not match to format yyyy-MM-dd!");
	    }
	    return date;
	}
}
