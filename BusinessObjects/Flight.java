/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author 92016097
 */
public class Flight {
    private String flightNumber;
    private String departureAirport;
    private String destinationAirport;
    private Double price;
    private Date dateTime;
    private String plane;
    private int seatsTaken;
    private SimpleDateFormat formatter;
    
    // Constructor for Flight class
    public Flight() {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }
    
    // Getter and setter methods for Flight class
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;    
    }
    
    public String getFlightNumber() {
        return this.flightNumber;    
    }
    
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;    
    }
    
    public String getDepartureAirport() {
        return this.departureAirport;    
    }
    
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;    
    }
    
    public String getDestinationAirport() {
        return this.destinationAirport;    
    }
        
    public void setPrice(Double price) {
        this.price = price;    
    }
    
    public Double getPrice() {
        return this.price;    
    }
    
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;    
    }
    
    public Date getDateTime() {
        return this.dateTime;    
    }
        
    public void setPlane(String plane) {
        this.plane = plane;    
    }
    
    public String getPlane() {
        return this.plane;    
    }
            
    public void setSeatsTaken(int seatsTaken) {
        this.seatsTaken = seatsTaken;    
    }
    
    public int getSeatsTaken() {
        return this.seatsTaken;    
    }
    
    // Overridden toString() method - returns String representation of 
    // dateTime object
    @Override
    public String toString() {
        return formatter.format(dateTime);
    }
}
