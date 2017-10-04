/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 92016097
 */
public class Booking {
    private int bookingNumber;
    private String flightNumber;
    private Double price;
    private String cabinClass;
    private int quantity;
    private boolean insurance;

    // Getter and setter methods for Booking class
    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }
    
    public int getBookingNumber() {
        return bookingNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }
    
    public String getCabinClass() {
        return cabinClass;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }
    
    public boolean getInsurance() {
        return insurance;
    }
}
