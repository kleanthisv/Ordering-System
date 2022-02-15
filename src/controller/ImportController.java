package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;



public class ImportController extends Controller<Session>{
   public final Session getSession() { return model; }
   
   private File selectedFile;
   
   @FXML
   private void handleSelectBtn(ActionEvent event) throws Exception{
       FileChooser fileChooser = new FileChooser();
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
       selectedFile = fileChooser.showOpenDialog(new Stage());
   }
   
   @FXML
   private void handleImport(ActionEvent event) throws Exception{
       System.out.println(selectedFile.getPath());
   }
}
