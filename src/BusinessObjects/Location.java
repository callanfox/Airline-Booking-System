/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 92016097
 */
package BusinessObjects;

public class Location {
    private String city;
    private String airportCode;
    
    // Getter and setter methods for City class
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
    
    public String getAirportCode() {
        return this.airportCode;
    }
    
    // Overridden toString() method - returns String of city and airport code
    // e.g: Auckland AKL
    @Override
    public String toString() {
        return city + " " + airportCode;
    }
}
