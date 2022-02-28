package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.*;



public class QuantityController extends Controller<Product>{
   public final Product getProduct() { return model; }
  
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
}