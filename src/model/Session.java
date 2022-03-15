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
        try{
            importOrders();
        } catch (Exception exc){
            System.out.println(exc.toString());
            exc.printStackTrace();
            System.out.println("Error importing orders.");
        }
    }
    
    public Suppliers getSuppliers(){
        return this.suppliers;
    }
        
    private void importData() throws Exception{
        //create AppData directory
        try{
            if(appDataDir.mkdir()) System.out.println("AppData directory dreated");
            else System.out.println("AppData directory already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with AppData directory");
        }
        
        //create Orders directory
        try{
            if(ordersDir.mkdir()) System.out.println("Orders directory dreated");
            else System.out.println("Orders directory already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with Orders directory");
        }
        
        //create Supplier csv
        try{
            if(suppliersCSV.createNewFile()) System.out.println("Suppliers.csv Created");
            else System.out.println("Suppliers.csv already exists");
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Error with Suppliers.csv");
        }
        
        //read in last import date
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

        BufferedReader reader = new BufferedReader(new FileReader(suppliersCSV));

        ArrayList<String> lines = new ArrayList<>();
        String sName = null;
                
        while ((sName = reader.readLine()) != null) {
            //suppliers.getSupplier(sName);
            lines.add(sName);
        }
        
        if(lines.size() == 1){
            String[] sList = lines.get(0).split(",");
            for(String s : sList){
                suppliers.getSupplier(s);
            }
        }
        else{
            for(String s : lines){
                suppliers.getSupplier(s);
            }
        }
        reader.close();        
    }
    
    private void importOrders() throws Exception {
        for (Supplier supplier : suppliers.getList()) {
            String orderPath = ordersDir + "\\" + supplier.getName() + ".csv";
            File orderFile = new File(orderPath);

            BufferedReader reader = new BufferedReader(new FileReader(orderFile));
            ArrayList<String> lines = new ArrayList<>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();
            
            for (String s : lines) { //split line into product and add to list
                if (!s.equals("product_title,variant_sku,net_quantity,bo_status")) {
                    if (s.replaceAll("[^,]", "").length() == 3) { //check if line has comma in it
                        String[] productArr = s.split(","); //0=title 1=sku 2=quantity
                        String productName = productArr[0];
                        String productSKU = productArr[1].trim();
                        int productQty = Integer.parseInt(productArr[2]);
                        Product p = new Product(productName, productSKU, productQty);
                        
                        //check if item is backorder
                        if(productArr[3].equals("true")) p.setBackorder(true);
                        supplier.getOrder().addProduct(p);
                    } else {
                        System.out.println("Error with line " + s);
                    }
                }
            }

        }
    }
    
    public void writeSuppliers() throws Exception{
        FileWriter csvWriter;
        csvWriter = new FileWriter(suppliersCSV,false);
	for(Supplier s : suppliers.getList()) {
            csvWriter.write(s.toString() + "\n");
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
    
    public void importSalesReport(File salesReport) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(salesReport));
        ArrayList<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        ArrayList<String> invalidProducts = new ArrayList<String>();

        for (String s : lines) { //split line into product and add to list
            if (!s.equals("product_title,product_vendor,variant_sku,net_quantity")) {
                if (s.replaceAll("[^,]", "").length() == 3) { //check if line has comma in it
                    String[] productArr = s.split(","); //0=title 1=supplier 2=sku 3=quantity
                    String supplierName = productArr[1].trim();
                    String productName = productArr[0].trim();
                    String productSKU = productArr[2].trim();
                    int productQty = Integer.parseInt(productArr[3]);
                    
                    if (supplierName.contains("/") || supplierName.contains("\\")) {
                        supplierName = supplierName.replace("/", "-");
                        supplierName = supplierName.replace("\\", "-");
                    }
                    if(supplierName.isEmpty()){
                        supplierName = "Custom Line";
                    }                    
                                        
                    try {
                        suppliers.getSupplier(supplierName).getOrder().addProduct(new Product(productName, productSKU, productQty));
                    } catch (Exception e) {
                        System.out.println(e.toString() + " when trying to import: " + s);
                       
                    }
                } else {
                    invalidProducts.add(s);
                }
            }
        }
        //show all the invalid products as errors
        for (String errors : invalidProducts) {
            Stage errorStage = new Stage();
            ViewLoader.showStage(new OSError("Error importing product: \n" + errors), "/view/Error.fxml", "ERROR", errorStage);
        }
    }
    
    public void importOrder(File orderFile) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(orderFile));
        ArrayList<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        
        ArrayList<String> invalidProducts = new ArrayList<String>();
        
        for (String s : lines) { //split line into product and add to list
            if (!s.equals("Product Name,SKU,Quantity,Backorder") && s.contains(",")) {
                
                if (s.replaceAll("[^,]", "").length() == 3) { //check if line has comma in it
                    
                    //declaring different variables
                    String[] productArr = s.split(","); //0=title 1=sku 2=qty 3=backorder
                    String supplierName = orderFile.getName().substring(0, orderFile.getName().indexOf("Order")).trim();
                    String productName = productArr[0].trim();
                    String productSKU = productArr[1].trim();
                    int productQty = Integer.parseInt(productArr[2]);
                    
                    boolean isBackorder = false;
                    if(productArr[3].equals("TRUE")){
                        isBackorder = true;
                    }
                    else{
                        isBackorder = false;
                    }
                    
                    //replace slashes with dashes
                    if (supplierName.contains("/") || supplierName.contains("\\")) {
                        supplierName = supplierName.replace("/", "-");
                        supplierName = supplierName.replace("\\", "-");
                    }

                    //if the product already exists in the order sheet, update the qty instead of adding new product
                    try {
                        suppliers.getSupplier(supplierName).getOrder().addProduct(new Product(productName, productSKU, productQty));
                    } catch (Exception e) {
                        System.out.println(e.toString() + " when trying to import: " + s);
                    }
                } else {
                    System.out.println("Error with line " + s);
                    invalidProducts.add(s);
                }

            }
        }
        //show all the invalid products as errors
        for (String errors : invalidProducts) {
            Stage errorStage = new Stage();
            ViewLoader.showStage(new OSError("Error importing product: \n" + errors), "/view/Error.fxml", "ERROR", errorStage);
        }
    }
    
    public void setSalesReport(File f){
        this.salesReportCSV = f;
        this.salesReportDate.set(salesReportCSV.getName().substring(6,salesReportCSV.getName().length()-4));
    }
    
    public File getSalesReport(){
        return this.salesReportCSV;
    }
    
    public StringProperty getReportDate(){
        return this.salesReportDate;
    }
    
    public void writeOrderCSV(Supplier s) throws Exception{
        String name = s.getName();
        if(name.contains("/") || name.contains("\\")){
            name = name.replace("\\", "-");
            name = name.replace("/", "-");
        }
        File path = new File(this.ordersDir + "\\" + name + ".csv");
        
        FileWriter orderWriter;
        orderWriter = new FileWriter(path, false);
        
        orderWriter.write("product_title,variant_sku,net_quantity,bo_status\n");
        
        for(Product p : s.getOrder().getList()){
            orderWriter.write(p.titleProperty().getValue() + ","); 
            orderWriter.write(p.SKUProperty().getValue() + ",");
            orderWriter.write(p.quantityProperty().getValue() + ",");
            orderWriter.write(p.backorderProperty().getValue() + "\n");
        }
                
        orderWriter.flush();
        orderWriter.close();
    }
}
