package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import model.*;



public class ErrorController extends Controller<OSError>{
   public final OSError getOSError() { return model; }
   
   @FXML private Label errorLbl;
   
   @FXML
   private void initialize(){
       stage.getIcons().add(new Image(ErrorController.class.getResourceAsStream("/view/icon.png")));
       errorLbl.setText(model.toString());
   }
   
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
}
