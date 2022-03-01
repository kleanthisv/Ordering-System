package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;



public class OrderController extends Controller<Supplier>{
   public final Supplier getSupplier() { return model; }
   public final ObservableList<Product> getList() { return model.getOrder().getList(); }
   
   @FXML ObservableList<Product> tempList;
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
       
       ObservableList<Product> tempList = this.getList().filtered(null);
       productTv.setItems(tempList);

       titleLbl.setText(model.getName() + " Order");
       
       productTv.getSelectionModel().selectedItemProperty().addListener((o, oldProd, newProd) -> {
            makeBackorderBtn.setDisable(newProd == null);
            adjustQtyBtn.setDisable(newProd == null);
            deleteProdBtn.setDisable(newProd == null);  
       });
       
       nameClm.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
       skuClm.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
       qtyClm.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
       
       
       //if product is backorder, change css so it is highlighted yellow or something

   }
   
   @FXML void handleAddProdBtn(ActionEvent event){
       
   }
   
   @FXML void handleDeleteProdBtn(ActionEvent event){
       Product p = getSelectedProduct();
       productTv.getSelectionModel().clearSelection();
       getList().remove(p);
   }
   
   @FXML void handleAdjustQtyBtn(ActionEvent event) throws Exception{
       Product p = getSelectedProduct();
       Stage qtyStage = new Stage();
       qtyStage.setWidth(100);
       qtyStage.setHeight(50);
       ViewLoader.showStage(p, "/view/Quantity.fxml", "Quantity Adjustment", qtyStage);
       productTv.getSelectionModel().clearSelection();
   }
   
   @FXML void handleMakeBackorderBtn(ActionEvent event){
       Product p = getSelectedProduct();
       p.setBackorder(true);
       
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
           
           stage.close();
       }
       catch(Exception e){
           System.out.println(e.toString());
           System.out.println("Error writing order.");
       }
   }
   
   private Product getSelectedProduct(){
       return productTv.getSelectionModel().getSelectedItem();
   }
}
