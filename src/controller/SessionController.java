package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.*;
import javafx.stage.Stage;
import model.*;



public class SessionController extends Controller<Session>{
   public final Session getSession() { return model; }
   public final ObservableList<Supplier> getList() { return model.getSuppliers(); }
    
   @FXML private TextField supplierTf;
   @FXML private ListView suppliersLv;
   @FXML private Label reportDateLbl;
   
   @FXML private void initialize(){
       reportDateLbl.textProperty().bind(model.getReportDate());
   }
   
   @FXML private void handleExit(ActionEvent event) throws Exception {
       model.writeSuppliers();
       this.stage.close();
   }
      
   @FXML private void handleAddSupplier(ActionEvent event) throws Exception {
       
       String supplierName = supplierTf.getText();
       
       if(!model.supplierExists(supplierName) && !supplierName.isEmpty()){
           model.getSuppliers().add(new Supplier(supplierName));
           System.out.println("Supplier " + supplierName + " added to supplier list.");
       }
       supplierTf.setText("");
   }
   
   @FXML private void handleImportSalesReportBtn(ActionEvent event) throws Exception{
       Stage importStage = new Stage();
       importStage.setHeight(50);
       importStage.setWidth(200);
       ViewLoader.showStage(model, "/view/Import.fxml", "Import Sales Report", importStage);
   }
}
