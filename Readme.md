### Author: Ivan Chernukha  
ivan.chernukha@studenti.unitn.it  

Done individually.  
### [http://assignment2-chernukha.herokuapp.com/](http://assignment2-chernukha.herokuapp.com/)

## Project    
### Code
The project was implemented 'as is' - no changes to model and API requirements were done according to the assignment description.  
However, Date field in activity class is represented as String and must contain date in format "yyyy-MM-dd".  
Activity types declared at class `rest/activity/model/Activity.java` as a final static string array. Due to an [issue](https://stackoverflow.com/questions/31220793/jersey-client-unable-to-convert-arraylist-to-xml) at jersey with producing a message writer for array list class, there's a wrapper class for the activity types to respond correctly for XML Request #6.    
Database initialization is done in static block at `PersonCollectionResource.java`class. That means when first time after the deployment a user calls `/person` end-point, this class is loaded and the database automatically been filled only once.  

### Tasks
There is an implementation for all tasks, except Extra Request #11. Please, take a look at log files at the [client repository](https://github.com/merryHunter/sde-assignment2-client.git).  


### Execution  
- Clone the repository  
- `ant create.war`  
- Place war file into tomcat `webapps` folder
- Run Tomcat and open browser to navigate and make requests. Example: `localhost:8081/ass2/person`.   







