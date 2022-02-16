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



public class ImportController extends Controller<Session>{
   public final Session getSession() { return model; }
   
   private File selectedFile;
   @FXML private Label salesReportLbl;
   @FXML ListView<Supplier> supplierLv = new ListView<Supplier>();
   
   @FXML
   private void initialize(){
       salesReportLbl.setText("No sales file selected.");
   }
   
   @FXML
   private void handleSelectBtn(ActionEvent event) throws Exception{
       FileChooser fileChooser = new FileChooser();
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
       selectedFile = fileChooser.showOpenDialog(new Stage());
       salesReportLbl.setText(selectedFile.getName());
   }
   
   @FXML
   private void handleImportBtn(ActionEvent event) throws Exception{
       //do stuff to split it into suppliers
   }
   
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
}
