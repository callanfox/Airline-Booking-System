/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uflybookingsystem;

/**
 *
 * @author 92016097
 */

// Abstract parent class of LocationImporter and FlightImporter.
public abstract class BaseImporter implements Runnable {
    private String fileName;
    private ImportResult results;
    
    public BaseImporter(String fileName) {
        this.fileName = fileName;
    }
     
    public void setResults(ImportResult results) {
        this.results = results;
    }
    
    public ImportResult getResults() {
        return results;
    }
    
    @Override
    public abstract void run();
}
