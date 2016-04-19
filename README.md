This android application uses the Publically exposed SF Bay APIs for 511 Real-Time Transit Departures.
The API Documentation is available at http://assets.511.org/pdf/RTT%20API%20V2.0%20Reference.pdf 
The APIs used for thie demo application are

1. Get List of all Agencies 
2. Get All Routes covered by each Agency
3. Get Various Stops for Each Route
4. Get Departure Details for Each Stop

User can run the application and hit the Get Agencies Button on the home screen which will fetch him the
list of all the plying agencies. User can select any one of the agency to further see the list of various
routes for that agency. Similarly, he can see the details for the various Stops for each route and correspondingly,
he an see the departure times for each stop.


  - The project structure has the standard android project structure with the java code residing in src/main/java directory
  - The package name for the project is com.hypertrack.departuretime
  - The drawable resource files are in the res directory.

  - The Code uses Android's Volley Library for making network calls to get API responses.

  - NetworkUtility.java class is used to get information weather the device has an active network connection or not,
    which would be used to make the metwork calls

  - Constants.java class contains constants which have been used through the entire code

  - String Constant have been defined in the strings.xml resource file

  - Custom ListViews have been used to display the data in a list format.



To get and run the the code on your system : -

1. Clone the project from the git repository "https://github.com/singhalyogesh/tracking"
2. Start Android Studio, and import the project that you have just cloned.








----------------------------------------------------------------------------------------------------------------------
Developer Info :

	Name : Yogesh Singhal
	email : yogeshsng@iitrpr.ac.in
	LinkedIn Profile : https://www.linkedin.com/in/yogesh-singhal-5584634b
	Github Profile : https://github.com/singhalyogesh

----------------------------------------------------------------------------------------------------------------------

