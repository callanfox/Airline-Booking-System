/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uflybookingsystem;

import java.util.ArrayList;
/**
 *
 * @author 92016097
 */
public class ImportResult {
    private int totalRows, importedRows, failedRows;
    private ArrayList<String> errorMessages = new ArrayList<>();
    
    // Constructor for ImportResult used to clear all values from instance variables 
    // when created.
    public ImportResult() {
        totalRows = 0;
        importedRows = 0;
        failedRows = 0;
        errorMessages.clear();
    }
    
    // Getter and setter methods for ImportResult class
    public void setTotalRows(int totalRows) {
        this.totalRows += totalRows;
    }
    
    public int getTotalRows() {
        return totalRows;
    }
    
    public void setImportedRows(int importedRows) {
        this.importedRows += importedRows;
    }
    
    public int getImportedRows() {
        return importedRows;
    }
    
    public void setFailedRows(int failedRows) {
        this.failedRows += failedRows;
    }
    
    public int getFailedRows() {
        return failedRows;
    }
    
    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }
    
    public void setErrorMessages(String msg) {
        errorMessages.add(msg);
    }
}
