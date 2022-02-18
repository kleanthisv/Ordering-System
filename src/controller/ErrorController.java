package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
