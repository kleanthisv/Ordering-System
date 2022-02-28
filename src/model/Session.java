package model;

import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;  
import java.util.ArrayList;
import java.util.Scanner;  
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.stage.Stage;


public class Session {
    
    private Suppliers suppliers;
    private String dataFolder = System.getenv("APPDATA");
    private StringProperty salesReportDate = new SimpleStringProperty() ;
    private final String titleString = "product_title,variant_sku,net_quantity\n";
    
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
        ArrayList<String> invalidProducts = new ArrayList<String>();
        
        for(String s : lines){ //split line into product and add to list
            if(!s.equals("product_title,product_vendor,variant_sku,net_quantity")){
                if(s.replaceAll("[^,]","").length() == 3){ //check if line has comma in it
                    String[] productArr = s.split(","); //0=title 1=supplier 2=sku 3=quantity
                    String supplierName = productArr[1].trim();
                    String productName = productArr[0];
                    String productSKU = productArr[2];
                    int productQty = Integer.parseInt(productArr[3]);
                 
                    if(!suppliers.hasSupplier(supplierName)){
                        suppliers.add(new Supplier(supplierName));
                    }
                    if(!productArr[2].isEmpty()){
                        try{
                            //if the product already exists in the order sheet, update the qty instead of adding new product
                            if(suppliers.getSupplier(supplierName).hasProduct(productSKU)){ 
                                Product p = suppliers.getSupplier(supplierName).getProduct(productSKU);
                                p.setQty(p.quantityProperty().getValue() + productQty);
                            }
                            else{
                                suppliers.getSupplier(supplierName).getOrder().addProduct(new Product(productName,productSKU,productQty));
                            }
                            
                        }
                        catch(Exception e){
                            System.out.println(e.toString() +" when trying to import: " + s);
                        }
                    }
                }
                else{
                    System.out.println("Error with line " + s);
                    invalidProducts.add(s);
                }
                
            }
        }
        //show all the invalid products as errors
        for(String errors: invalidProducts){
            Stage errorStage = new Stage();
            errorStage.setHeight(100);
            errorStage.setWidth(200);
            ViewLoader.showStage(new OSError("Error importing product: \n" + errors), "/view/Error.fxml", "ERROR", errorStage);
        }
    }
    
    public void setSalesReport(File f){
        this.salesReportCSV = f;
        this.salesReportDate.set(salesReportCSV.getName().substring(6,salesReportCSV.getName().length()-4));
    }
    
    public StringProperty getReportDate(){
        return this.salesReportDate;
    }
    
    public void writeOrderCSV(Supplier s) throws Exception{
        File path = new File(this.ordersDir + "\\" + s.getName() + ".csv");
        
        FileWriter orderWriter;
        orderWriter = new FileWriter(path, false);
        
        orderWriter.write(titleString);
        
        for(Product p : s.getOrder().getList()){
            orderWriter.write(p.titleProperty().getValue() + ","); 
            orderWriter.write(p.SKUProperty().getValue() + ",");
            orderWriter.write(p.quantityProperty().getValue() + "\n");
            System.out.println("Wrote " + p.SKUProperty().getValue());
        }
        
        orderWriter.flush();
        orderWriter.close();
    }
}
