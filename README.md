#Booking Application Execution Instruction

##: Docker Instruction

1. Please ensure you have Docker running on your machine

2. Open comman prompt or Terminal and navigate to the project root Directory
    * Run the command "docker-compose up"
	
3. Wait for successful instation of docker containers for both the service and postgres database

4. Open Powershell if you are on Windows or Terminal if you are on Mac, Run "Docker ps" to view list of containers
	
Endpoints

* View All Trails: http://localhost:8080/api/v1/trails/ ,  Method GET
* View Selected Trail : http://localhost:8080/api/v1/trails/{trailID} Where {trailID} is the ID of a particular trailID, Method Get
* Book a Selected Trail :http://localhost:8080/api/v1/booking/book-trail  Method POST
   Sample Data 
   {
  "trailId":2,
   "bookDate": "2022-05-23",
  "hikers":[    
    {"firstName":"John", "lastName":"Doe", "age":23},    
    {"firstName":"Mary", "lastName":"Doe", "age":25},  
    {"firstName":"George", "lastName":"Hike", "age":53}   
   ]
  } 

*View a Booking : http://localhost:8080/api/v1/booking/view-booking/{bookingid}   Method GET
*Cancel Booking : http://localhost:8080/api/v1/booking//cancel-booking/{bookingId} Method GET