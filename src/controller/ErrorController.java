package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.*;



public class ErrorController extends Controller<OSError>{
   public final OSError getOSError() { return model; }
   
   @FXML private Label errorLbl;
   
   @FXML
   private void initialize(){
       errorLbl.setText(model.toString());
   }
   
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
}
