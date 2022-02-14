package model;

import java.io.*;  
import java.util.Scanner;  
import java.util.logging.Level;
import java.util.logging.Logger;


public class Session {
    private Suppliers suppliers;
    
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
    
    private void updateSuppliers() throws Exception{
        
    }
    
    private void importSuppliers() throws Exception{
        String dataFolder = System.getenv("APPDATA");
        
        File appDataDir = new File(dataFolder + "\\MX5MANIA Ordering System");
        if(appDataDir.mkdir()) System.out.println("AppData directory dreated");
        else System.out.println("AppData directory could not be created");
        
        File suppliersCSV = new File(appDataDir + "\\Suppliers.csv");
        if(suppliersCSV.createNewFile()) System.out.println("Suppliers.csv Created");
        else System.out.println("Suppliers.csv already exists");
        
        Scanner sc = new Scanner(suppliersCSV);
        sc.useDelimiter(",");
        while(sc.hasNext()){
            suppliers.add(new Supplier(sc.next()));
        }
    }
    
}
