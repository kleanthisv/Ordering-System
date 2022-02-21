package model;

import java.io.*;  
import java.util.Scanner;  
import javafx.beans.property.*;
import javafx.collections.ObservableList;


public class Session {
    
    private Suppliers suppliers;
    private String dataFolder = System.getenv("APPDATA");
    private StringProperty salesReportDate = new SimpleStringProperty() ;
    
    private File appDataDir = new File(dataFolder + "\\MX5MANIA Ordering System");
    private File suppliersCSV = new File(appDataDir + "\\Suppliers.csv");
    private File dateFile = new File(appDataDir + "\\Date.txt");
    private File salesReportCSV;
    
    public Session(){
        this.suppliers = new Suppliers();
        try {
            importSuppliers();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
            System.out.println("Error importing suppliers.");
        }
    }
    
    public ObservableList<Supplier> getSuppliers(){
        return this.suppliers.getSuppliers();
    }
    
    public boolean supplierExists(String name){
        return this.suppliers.hasSupplier(name);
    }
    
    private void importSuppliers() throws Exception{
        
        try{
            if(appDataDir.mkdir()) System.out.println("AppData directory dreated");
            else System.out.println("AppData directory already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with AppData directory");
        }
        
        try{
            if(suppliersCSV.createNewFile()) System.out.println("Suppliers.csv Created");
            else System.out.println("Suppliers.csv already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with Suppliers.csv");
        }
        
        try{
            if(dateFile.createNewFile()) System.out.println("ImportDate.txt Created");
            else System.out.println("ImportDate.txt already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with ImportDate.txt");
        }
        
        
        Scanner supplierSc = new Scanner(suppliersCSV);
        supplierSc.useDelimiter(",");
        while(supplierSc.hasNext()){
            suppliers.add(new Supplier(supplierSc.next()));
        }
        supplierSc.close();
        
        Scanner dateSc = new Scanner(dateFile);
        if(dateSc.hasNext()){
            this.salesReportDate.set(dateSc.next());
        }
        dateSc.close();
    }
    
    public void writeSuppliers() throws Exception{
        FileWriter csvWriter;
        csvWriter = new FileWriter(suppliersCSV,false);
	for(Supplier s : suppliers.getSuppliersAsArr()) {
            csvWriter.write(s.toString() + ",");
	}
        System.out.println("Suppliers CSV finished writing.");
        csvWriter.flush();
        csvWriter.close();
        
        if(!salesReportDate.getValueSafe().isEmpty()){
            FileWriter dateWriter;
            dateWriter = new FileWriter(dateFile,false);
            dateWriter.write(salesReportDate.getValue());
            dateWriter.flush();
            dateWriter.close();
        }
        
    }
    
    public void setSalesReport(File f){
        this.salesReportCSV = f;
        this.salesReportDate.set(salesReportCSV.getName().substring(6,salesReportCSV.getName().length()-4));
    }
    
    public StringProperty getReportDate(){
        return this.salesReportDate;
    }
}
