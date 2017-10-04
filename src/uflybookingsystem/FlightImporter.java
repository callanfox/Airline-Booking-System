/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uflybookingsystem;

import BusinessObjects.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.*;


/**
 *
 * @author 92016097
 */
public class FlightImporter extends BaseImporter {
    private String fileData = "";
    private String firstLine;
    private String[] columns;
    private int lineNum = 1;
    private Pattern pattern;
    private Matcher matcher; 
    private Plane planeType;
    private Flight flightToUpdate = null;
    private Flight flightToAdd = null;
    private final ImportResult result = new ImportResult();
    private final File file;
    private BigDecimal price;
    private Integer seatsTaken;
    private String dateTime; 
    
    public FlightImporter(String fileName){
        super(fileName);
        super.setResults(result);
        file = new File(fileName);
        flightToUpdate = new Flight();
        flightToAdd = new Flight();
        pattern = Pattern.compile("[A-Z]{2}\\d{3}");
    }
    
    @Override
    public void run() {
        try (FileReader fileReader = new FileReader(file)) {
           for(int c = fileReader.read(); c != -1; c = fileReader.read()) {
                fileData += (char) c;
            }
            int counter = 0;
            String[] lines = fileData.replace("\r\n", "\n").replace("\r", "\n").split("\n");
            firstLine = lines[0];
            columns = firstLine.split(",");
            if(columns.length == 7) {
                if(columns[0].equals("Flight Number") && columns[1].equals("Departure Airport")  && columns[2].equals("Destination Airport") && columns[3].equals("Price") && columns[4].equals("DateTime") && columns[5].equals("Plane") && columns[6].equals("Seats Taken")) {
                    lines = Arrays.copyOfRange(lines, 1, lines.length);
                }
            }
            for (String line : lines) {
                try {
                    counter++;
                    if(line != null) {
                        super.getResults().setTotalRows(1);
                        columns = line.split(",");
                        if(columns.length != 7) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Insufficent number of columns (7 required) for this object type.");
                        }
                        else if(columns[0].equals("")) {
                            getResults().setFailedRows(1); 
                            result.setErrorMessages("Flight Number missing (line " + counter + ").");
                        }
                        else if(columns[1].equals("")) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Departure Airport missing (line " + counter + ").");
                        }
                        else if(columns[2].equals("")) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Destination Airport missing (line " + counter + ").");
                        }
                        else if(columns[5].equals("")) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Airplane type missing (line " + counter + ").");
                        }
                        matcher = pattern.matcher(columns[0]);
                        if(matcher.find() == false) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Invalid pattern in detected flight number (line " + counter + ").");
                        }
                        try {
                            Double doublePrice = Double.parseDouble(columns[3]);
                            price = BigDecimal.valueOf(doublePrice);
                        }
                        catch(NumberFormatException nfe) {
                            getResults().setErrorMessages("Value not of numerical type. Please check appropriate sources.");
                        }
                        try {
                            seatsTaken = Integer.parseInt(columns[6]);
                        }
                        catch(NumberFormatException nfe) {
                            getResults().setErrorMessages("Value not of numerical type. Please check appropriate sources.");
                        }
                        try {
                            dateTime = (columns[4]);    
                        }
                        catch(Exception e) {
                                getResults().setErrorMessages("Value not of valid type. Please check appropriate sources.");
                        }
                    }
                }
                catch(Exception ioe) {
                    ;
                }
                
                boolean isPlaneInEnumeration = false;
                for (Plane plane : Plane.values()) {
                    if(plane.name().equals(columns[5])) {
                       planeType = plane; 
                       isPlaneInEnumeration = true;
                    }
                }   
                    
                if (seatsTaken > planeType.getPassengerCapacity()) {
                    getResults().setErrorMessages("Seats exceed required capacity.");
                }
                    
                flightToUpdate = DatabaseOperations.getFlightByFlightNumber(columns[0]);
                    
                if(flightToUpdate.getFlightNumber() == null) {
                    flightToAdd.setFlightNumber(columns[0]);
                    flightToAdd.setDepartureAirport(columns[1]);
                    flightToAdd.setDestinationAirport(columns[2]);
                    flightToAdd.setPrice(price);
                    flightToAdd.setDateTime(dateTime);
                    flightToAdd.setPlane(planeType.toString());
                    flightToAdd.setSeatsTaken(seatsTaken);
                    DatabaseOperations.AddFlight(flightToAdd);
                }
                else {
                    flightToUpdate.setFlightNumber(columns[0]);
                    flightToUpdate.setDepartureAirport(columns[1]);
                    flightToUpdate.setDestinationAirport(columns[2]);
                    flightToUpdate.setPrice(price);
                    flightToUpdate.setDateTime(dateTime);
                    flightToUpdate.setPlane(planeType.toString());
                    flightToUpdate.setSeatsTaken(seatsTaken);
                    DatabaseOperations.UpdateFlight(flightToUpdate);
                    }
                    getResults().setImportedRows(1);
                }
                
                } 
                catch (IOException ioe) {
                    result.setErrorMessages("Error: IOException occurred.");
                }
                catch (Exception ex) {
                    result.setErrorMessages("Error: Unknown error occurred.");
                }
                finally {
                    lineNum += 1;
                }
            }    
        }   

