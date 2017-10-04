/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uflybookingsystem;

import BusinessObjects.Location;
import java.io.*;
import java.util.Arrays;
import java.util.regex.*;

/**
 *
 * @author Callan
 */
public class LocationImporter extends BaseImporter {  
    private String fileData = "";
    private String firstLine;
    private String[] columns;
    private int lineNum = 1;
    private final Pattern pattern;
    private Matcher matcher; 
    private Location locationToUpdate = null;
    private Location locationToAdd = null;
    private final ImportResult result = new ImportResult();
    private final File file;
    
    public LocationImporter(String fileName){
        super(fileName);
        super.setResults(result);
        file = new File(fileName);
        locationToUpdate = new Location();
        locationToAdd = new Location();
        pattern = Pattern.compile("\\w{3}");
    }
    
    @Override
    public void run() {
        try (FileReader fileReader = new FileReader(file)) {
            for(int c = fileReader.read(); c != -1; c = fileReader.read()) {
                fileData += (char) c;
            }
            String[] lines = fileData.replace("\r\n", "\n").replace("\r", "\n").split("\n");
            firstLine = lines[0];
            columns = firstLine.split(",");
            if(columns.length == 2) {
                if(columns[0].equals("City") && columns[1].equals("Airport Code")) {
                    lines = Arrays.copyOfRange(lines, 1, lines.length);
                }
            }
            for (String line : lines) {
                try {
                    if(line != null) {
                        super.getResults().setTotalRows(1);
                        columns = line.split(",");
                        if(columns.length != 2) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Error: Location file should contain 2 columns ('City' and 'Airport Code).");
                        }
                        else if(columns[0].equals("")) {
                            getResults().setFailedRows(1); 
                            result.setErrorMessages("Error: No 'City' column found.");
                        }
                        else if(columns[1].equals("")) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Error: No 'Airport Code' column found.");
                        }
                        matcher = pattern.matcher(columns[1]);
                        if(matcher.find() == false) {
                            getResults().setFailedRows(1);
                            result.setErrorMessages("Error: Invalid Airport Code.");
                        }  
                    }
                    locationToUpdate = DatabaseOperations.getLocationByAirportCode(columns[1]);
                    if(locationToUpdate.getAirportCode() == null) {
                        locationToAdd.setCity(columns[0]);
                        locationToAdd.setAirportCode(columns[1]);
                        DatabaseOperations.AddLocation(locationToAdd);
                        locationToUpdate = locationToAdd;
                        DatabaseOperations.UpdateLocation(locationToUpdate);
                    }
                    getResults().setImportedRows(1);
                }
                finally {
                    lineNum += 1;
                }
            }
        } 
        catch (IOException ioe) {
            result.setErrorMessages("Error: IOException occurred.");
        }
        catch (Exception ex) {
            result.setErrorMessages("Error: Unknown error occurred.");
        } 
    }
}