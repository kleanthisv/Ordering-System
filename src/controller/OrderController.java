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
//   ObservableList<Product> productList = model.getOrder().getList();
           
   @FXML
   private void initialize(){
       titleLbl.setText(model.getName() + " Order");
       //initialize table view.
       //if product is backorder, change css so it is highlighted yellow or something
   }
   
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
   
   @FXML
   private void handleSaveExitBtn(ActionEvent event) throws Exception{
       //rewrite order file and close window
   }
}
