package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
   private void handleSelectBtn(ActionEvent event){
       try{
           FileChooser fileChooser = new FileChooser();
           fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
           File tempFile = fileChooser.showOpenDialog(new Stage());
           if(tempFile.isFile()){
               selectedFile = tempFile;
               salesReportLbl.setText(selectedFile.getName());
            }
       }
       catch(Exception e){
           System.out.println("No file chosen.");
       }
   }
   
   @FXML
   private void handleImportBtn(ActionEvent event) throws Exception{
       
       try{
            String fileName = selectedFile.getName();
            if(fileName.matches("sales_[0-9]{4}-[0-9]{2}-[0-9]{2}.csv")){
                model.setSalesReport(selectedFile);
            }
            else{
                Stage errorStage = new Stage();
                errorStage.setHeight(100);
                errorStage.setWidth(200);
                ViewLoader.showStage(new OSError("Selected file is not a sales report"), "/view/Error.fxml", "ERROR", errorStage);
            }
        }
        catch(NullPointerException e){
            Stage errorStage = new Stage();
            errorStage.setHeight(100);
            errorStage.setWidth(200);
            ViewLoader.showStage(new OSError("No file selected"), "/view/Error.fxml", "ERROR", errorStage);
        }
    }
      
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
}
