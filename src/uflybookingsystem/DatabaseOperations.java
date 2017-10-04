/*
 * This class contains all the common operations that involve retreiving data from the database, 
 * saving data to the database or updating existing database data 
 * for all three tables (Location, Flight and Booking)
 */
package uflybookingsystem;

import java.sql.*;
import java.util.ArrayList;
import BusinessObjects.*;



public class DatabaseOperations {
    private static ArrayList<Location> arrayOfLocations;
    private static ArrayList<Flight> arrayOfFlightsByLocation;
    private static ArrayList<Flight> arrayAllFlights;
    
    // Returns all information from the Location table in the uFly database.
    public static ArrayList<Location> GetAllLocations(){
        arrayOfLocations = new ArrayList<>();
        Location location = new Location();
        try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from location")){
            while (resultSet.next()) {
                location = new Location();
                location.setCity(resultSet.getString("City"));
                location.setAirportCode(resultSet.getString("AirportCode"));
                arrayOfLocations.add(location);
            }
        }
        catch(SQLException sqle){
           sqle.toString();
        }
        return arrayOfLocations;
    }
    
    // Method returns all information from the Flight table in the uFly database.
    public static ArrayList<Flight> GetAllFlights(String departure, String destination){
        arrayAllFlights = new ArrayList<Flight>();
        Flight flight = new Flight();
        String sqlStatement = "SELECT * from flight WHERE DepartureAirport = '" + departure + "' AND DestinationAirport = '" + destination + "'";
        try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement)){
            while (resultSet.next()) {
                flight = new Flight();
                flight.setFlightNumber(resultSet.getString("FlightNumber"));
                flight.setDestinationAirport(resultSet.getString("DestinationAirport"));
                flight.setDepartureAirport(resultSet.getString("DepartureAirport"));
                flight.setPrice(resultSet.getBigDecimal("Price"));
                flight.setDateTime(resultSet.getString("DateTime"));
                flight.setPlane(resultSet.getString("Plane"));
                flight.setSeatsTaken(resultSet.getInt("SeatsTaken"));
                arrayAllFlights.add(flight);
            }
        }
        catch(SQLException sqle){
           sqle.toString();
        }
        return arrayAllFlights;
    }
    
    public static ArrayList<Flight> GetAllFlights(){
        arrayAllFlights = new ArrayList<Flight>();
        Flight flight = new Flight();
        String sqlStatement = "SELECT * from flight";
        try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement)){
            while (resultSet.next()) {
                flight = new Flight();
                flight.setFlightNumber(resultSet.getString("FlightNumber"));
                flight.setDestinationAirport(resultSet.getString("DestinationAirport"));
                flight.setDepartureAirport(resultSet.getString("DepartureAirport"));
                flight.setPrice(resultSet.getBigDecimal("Price"));
                flight.setDateTime(resultSet.getString("DateTime"));
                flight.setPlane(resultSet.getString("Plane"));
                flight.setSeatsTaken(resultSet.getInt("SeatsTaken"));
                arrayAllFlights.add(flight);
            }
        }
        catch(SQLException sqle){
            System.out.println(sqle.toString());
        }
        return arrayAllFlights;
    }
    
    // Obtains all the information from the Flight table based on the departure/destination airports and travel date.
    @SuppressWarnings("empty-statement")
    public static ArrayList<Flight> GetAllFlightsForLocation(String departure, String destination, String travelDate){
        arrayOfFlightsByLocation = new ArrayList<Flight>();
        String sqlStatement = "SELECT * from flight WHERE DepartureAirport = '" + departure + "' AND DestinationAirport = '" + destination + "' AND DateTime='" + travelDate + "'";
        try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement)){
            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setDepartureAirport(resultSet.getString("DepartureAirport"));
                flight.setDestinationAirport(resultSet.getString("DestinationAirport"));
                flight.setDateTime(resultSet.getString("DateTime"));
                arrayOfFlightsByLocation.add(flight);
            }
        }
        catch(SQLException sqle){
          sqle.toString();
        }
        return arrayOfFlightsByLocation;
    }
    
    // Adds booking passed as a parameter to the Booking table in the uFly database.
    // Note that Booking number is set as an incrementing field, so it doesn't need to be set.
    public static void AddBooking(Booking booking){
    	try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM booking")){
            resultSet.moveToInsertRow();
            resultSet.updateString("FlightNumber", booking.getFlightNumber());
            resultSet.updateBigDecimal("Price", booking.getPrice());
            resultSet.updateObject("CabinClass", booking.getCabinClass().toString());
            resultSet.updateInt("Quantity", booking.getQuantity());
            resultSet.updateBoolean("Insurance", booking.getInsurance());
            resultSet.insertRow();
        }
        catch(SQLException sqle){
            sqle.toString();
        }
    }
    
    // Obtains the flight based on the flightNumber parameter.
    public static Flight getFlightByFlightNumber(String flightNumber){
        String sqlStatement = "SELECT * FROM flight WHERE FlightNumber = '" + flightNumber + "'";
        Flight flight = new Flight();
            try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(sqlStatement)){
            resultSet.moveToInsertRow();
            resultSet.updateString("FlightNumber", flight.getFlightNumber());
            resultSet.updateString("DepartureAirport", flight.getDepartureAirport());
            resultSet.updateString("DestinationAirport", flight.getDestinationAirport());
            resultSet.updateBigDecimal("Price", flight.getPrice());
            resultSet.updateString("DateTime", flight.getDateTime());
            resultSet.updateString("Plane", flight.getPlane());
            resultSet.updateInt("SeatsTaken", flight.getSeatsTaken());
            resultSet.insertRow();
        }
        catch(SQLException sqle){
          sqle.toString();
        }
        return flight;
    }
    
     // Obtains the location based on the airportCode parameter.
   public static Location getLocationByAirportCode(String airportCode){
       Location location = new Location();
	try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("SELECT * from location")){
            resultSet.moveToInsertRow();
            resultSet.updateString("City", location.getCity());
            resultSet.updateString("AirportCode", location.getAirportCode());
            resultSet.insertRow();
        }
        catch(SQLException sqle){
           sqle.toString();
        } 
        return location;
    }
    
    // Adds location passed as a parameter to the Location table in the uFly database.
    public static void AddLocation(Location location){
	try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("SELECT * from Location")){
            resultSet.moveToInsertRow();
            resultSet.updateString("City", location.getCity());
            resultSet.updateString("AirportCode", location.getAirportCode());
            resultSet.insertRow();
        }
        catch(SQLException sqle){
           sqle.toString();
        } 
    }
     
    // Adds a flight passed as a parameter to the Flight table in the uFly database
    public static void AddFlight(Flight flight){
        try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Flight")){
            resultSet.moveToInsertRow();
            resultSet.updateString("FlightNumber", flight.getFlightNumber());
            resultSet.updateString("DepartureAirport", flight.getDepartureAirport());
            resultSet.updateString("DestinationAirport", flight.getDestinationAirport());
            resultSet.updateBigDecimal("Price", flight.getPrice());
            resultSet.updateString("DateTime", flight.getDateTime());
            resultSet.updateString("Plane", flight.getPlane());
            resultSet.updateInt("SeatsTaken", flight.getSeatsTaken());
            resultSet.insertRow();
        }
        catch(SQLException sqle){
           sqle.toString();
        }
    }

    
    // Updates the location to the one passed to it as a parameter where the airport codes are matching.
    public static void UpdateLocation(Location location){
        String sqlStatement = "SELECT * FROM location WHERE City ='" + location.getCity() + "' AND AirportCode ='" + location.getAirportCode() + "'";
        try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(sqlStatement)){
            resultSet.absolute(1);
            resultSet.updateString("City", location.getCity());
            resultSet.updateString("AirportCode", location.getAirportCode());
            resultSet.updateRow();
        }
        catch(SQLException sqle){
           sqle.toString();
        } 
    }
    
    // Updates the flight to the one passed to it as a parameter where the flight numbers are matching.
    public static void UpdateFlight(Flight flight) throws SQLException{
        String sqlStatement = "SELECT * FROM flight WHERE FlightNumber = '" + flight.getFlightNumber() + "'";
        try (Connection connection = DbConnector.connectToDb();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(sqlStatement)){
            resultSet.absolute(1);
            resultSet.updateInt("SeatsTaken", flight.getSeatsTaken());
            resultSet.updateRow();
        }
        catch(SQLException sqle){
           sqle.toString();
        }
    }
}

