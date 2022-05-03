package controller;

import au.edu.uts.ap.javafx.Controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import model.*;

public class ChangeSupplierController extends Controller<Product> {

    public final Product getProduct() {
        return model;
    }

    @FXML ListView suppliersLv;
    @FXML TextField filterTf;
    
    private String dataFolder = System.getenv("APPDATA");
    private File suppliersCSV = new File(dataFolder + "\\MX5MANIA Ordering System" + "\\Suppliers.csv");
    
    private Suppliers suppliers = new Suppliers();
    
    @FXML
    private void initialize() {
        stage.getIcons().add(new Image(AddProductController.class.getResourceAsStream("/view/icon.png")));
        
        try{
            importSuppliers();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        FilteredList<Supplier> filteredSuppliers = new FilteredList<>(suppliers.getList(), s -> true);
        
        suppliersLv.setItems(filteredSuppliers);
        
        filterTf.textProperty().addListener( obs -> {
            String filter = filterTf.getText();
            if(filter == null || filter.length() == 0){
                filteredSuppliers.setPredicate(s -> true);
            }
            else{
                filteredSuppliers.setPredicate(s -> s.getName().toLowerCase().contains(filter.toLowerCase()));
            }
        });
    }
    
    @FXML
    private void handleSetSupplierBtn(ActionEvent event){
        getSelectedSupplier().getOrder().addProduct(getProduct());
        stage.close();
    }
    
    @FXML
    private void handleCloseBtn(ActionEvent event) {
        stage.close();
    }
    
    private Supplier getSelectedSupplier(){
        return (Supplier) suppliersLv.getSelectionModel().getSelectedItem();
    }
    
    private void importSuppliers() throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader(suppliersCSV));

        ArrayList<String> lines = new ArrayList<>();
        String sName = null;
                
        while ((sName = reader.readLine()) != null) {
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
}
