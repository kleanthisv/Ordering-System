package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;



public class SessionController extends Controller<Session>{
   public final Session getSession() { return model; }
    
   @FXML private TextField supplierTf;
   
   @FXML private void handleExit(ActionEvent event) throws Exception {
       //model.updateSuppliers();
       this.stage.close();
   }
   
   @FXML private void handlePrintSupp(ActionEvent event) throws Exception {
       model.getSuppliers().printSuppliers();
   }
   
   @FXML private void handleAddSupplier(ActionEvent event) throws Exception {
       
       String supplierName = supplierTf.getText();
       
       if(!model.supplierExists(supplierName) && !supplierName.isEmpty()){
           model.getSuppliers().add(new Supplier(supplierName));
           System.out.println("Supplier " + supplierName + " added to supplier list.");
       }       
       supplierTf.setText("");
   }
}
