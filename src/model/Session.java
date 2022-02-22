package model;

import java.io.*;  
import java.util.ArrayList;
import java.util.Scanner;  
import javafx.beans.property.*;
import javafx.collections.ObservableList;


public class Session {
    
    private Suppliers suppliers;
    private String dataFolder = System.getenv("APPDATA");
    private StringProperty salesReportDate = new SimpleStringProperty() ;
    
    public final File appDataDir = new File(dataFolder + "\\MX5MANIA Ordering System");
    public final File ordersDir = new File(appDataDir + "\\Orders");
    public final File suppliersCSV = new File(appDataDir + "\\Suppliers.csv");
    public final File dateFile = new File(appDataDir + "\\Date.txt");
    private File salesReportCSV;
    
    public Session(){
        this.suppliers = new Suppliers();
        try {
            importData();
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
    
    private void importData() throws Exception{
        try{
            if(appDataDir.mkdir()) System.out.println("AppData directory dreated");
            else System.out.println("AppData directory already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with AppData directory");
        }
        
        try{
            if(ordersDir.mkdir()) System.out.println("Orders directory dreated");
            else System.out.println("Orders directory already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with Orders directory");
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
        Scanner dateSc = new Scanner(dateFile);
        if(dateSc.hasNext()){
            this.salesReportDate.set(dateSc.next());
        }
        dateSc.close();
    }
    
    private void importSuppliers() throws Exception{

        Scanner supplierSc = new Scanner(suppliersCSV);
        supplierSc.useDelimiter(",");
        while(supplierSc.hasNext()){
            suppliers.add(new Supplier(supplierSc.next()));
        }
        supplierSc.close();        
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
    
    public void importSalesReport(File salesReport) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(salesReport));
        ArrayList<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
            
        }
        //list of all incoming products, to be sorted into Orders by supplier name
        
        for(String s : lines){ //split line into product and add to list
            if(!s.equals("product_title,product_vendor,variant_sku,net_quantity")){
                if(s.replaceAll("[^,]","").length() == 3){ //check if line has comma in it
                    String[] productArr = s.split(","); //0=title 1=supplier 2=sku 3=quantity
                    productArr[1] = productArr[1].trim();
                    
                    if(!suppliers.hasSupplier(productArr[1])){
                        suppliers.add(new Supplier(productArr[1]));
                    }
                    if(!productArr[2].isEmpty() && suppliers.hasSupplier(productArr[1])){
                        try{
                            suppliers.getSupplier(productArr[1]).getOrder().addProduct(new Product(productArr[0],productArr[2],Integer.parseInt(productArr[3])));
                        }
                        catch(Exception e){
                            System.out.println(e.toString() +" when trying to import: " + s);
                        }
                    }
                }
                else{
                    System.out.println("Line " + s + " includes a comma.");
                }
            }
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
