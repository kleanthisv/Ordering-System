package model;

import java.io.*;  
import java.util.Scanner;  
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;


public class Session {
    
    private Suppliers suppliers;
    private String dataFolder = System.getenv("APPDATA");
    private File appDataDir = new File(dataFolder + "\\MX5MANIA Ordering System");
    private File suppliersCSV = new File(appDataDir + "\\Suppliers.csv");
    
    public Session(){
        this.suppliers = new Suppliers(); // read in Suppliers csv
        try {
            importSuppliers();
        } catch (Exception ex) {
            System.out.println("suppliers.csv not found");
        }
    }
    
    public Suppliers getSuppliers(){
        return this.suppliers;
    }
    
    public boolean supplierExists(String name){
        return this.suppliers.hasSupplier(name);
    }
    
    private void importSuppliers() throws Exception{
        
        if(appDataDir.mkdir()) System.out.println("AppData directory dreated");
        else System.out.println("AppData directory could not be created");
        
        if(suppliersCSV.createNewFile()) System.out.println("Suppliers.csv Created");
        else System.out.println("Suppliers.csv already exists");
        
        Scanner sc = new Scanner(suppliersCSV);
        sc.useDelimiter(",");
        while(sc.hasNext()){
            suppliers.add(new Supplier(sc.next()));
        }
    }
    
    public void writeSuppliers() throws Exception{
        FileWriter csvWriter;
        csvWriter = new FileWriter(suppliersCSV,false);
	for(Supplier s : suppliers.getSuppliersAsArr()) {
            csvWriter.write(s.getName() + ",");
	}
        System.out.println("Suppliers CSV finished writing.");
        csvWriter.flush();
        csvWriter.close();
    }
    
}
