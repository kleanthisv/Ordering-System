package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.Stage;
import model.*;



public class OrderController extends Controller<Supplier>{
   public final Supplier getSupplier() { return model; }
   public final ObservableList<Product> getList() { return model.getOrder().getList(); }
   
   @FXML TableView<Product> productTv = new TableView<Product>();
   @FXML Label titleLbl;
   
   @FXML TableColumn<Product, String> nameClm;
   @FXML TableColumn<Product, String> skuClm;
   @FXML TableColumn<Product, Integer> qtyClm;
   
   @FXML Button makeBackorderBtn;
   @FXML Button adjustQtyBtn;
   @FXML Button deleteProdBtn;
   
           
   @FXML
   private void initialize(){
       titleLbl.setText(model.getName() + " Order");
       
       productTv.getSelectionModel().selectedItemProperty().addListener((o, oldProd, newProd) -> 
               makeBackorderBtn.setDisable(newProd == null)
       
       );
       
       nameClm.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
       skuClm.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
       qtyClm.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
       //initialize table view.
       //if product is backorder, change css so it is highlighted yellow or something
   }
   
   @FXML void handleAddProdBtn(ActionEvent event){
       
   }
   
   @FXML void handleDeleteProdBtn(ActionEvent event){
       
   }
   
   @FXML void handleAdjustQtyBtn(ActionEvent event){
       
   }
   
   @FXML void handleMakeBackorderBtn(ActionEvent event){
       
   }
   
   @FXML void handleExportSelectedBtn(){
       
   }
   
   @FXML void handleExportAllBtn(){
       
   }
   
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
   
   @FXML
   private void handleSaveExitBtn(ActionEvent event){
       try{
           model.writeOrder();
           stage.close();
       }
       catch(Exception e){
           System.out.println(e.toString());
           System.out.println("Error writing order.");
       }
   }
}
