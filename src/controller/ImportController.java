package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;



public class ImportController extends Controller<Session>{
   public final Session getSession() { return model; }
   
   private File selectedFile;
   private File selectedOrderFile;
   @FXML private Label salesReportLbl;
   @FXML private Label orderLbl;
   @FXML ListView<Supplier> supplierLv = new ListView<Supplier>();
   @FXML Button importBtn;
   @FXML Button importOrderBtn;
   
   @FXML
   private void initialize(){
       stage.getIcons().add(new Image(ImportController.class.getResourceAsStream("/view/icon.png")));
       salesReportLbl.setText("No sales file selected.");
       orderLbl.setText("No order file selected.");
       importBtn.setDisable(true);
       importOrderBtn.setDisable(true);
   }
   
   @FXML
   private void handleSelectBtn(ActionEvent event){
       try{
           FileChooser fileChooser = new FileChooser();
           fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
           File tempFile = fileChooser.showOpenDialog(new Stage());
           String fileName = tempFile.getName();
           if(tempFile.isFile() && 
           (fileName.matches("sales_[\\d]{4}-[\\d]{2}-[\\d]{2}.csv") || fileName.matches("sales_[\\d]{4}-[\\d]{2}-[\\d]{2}_[\\d]{4}-[\\d]{2}-[\\d]{2}.csv"))){
               if (!tempFile.getAbsolutePath().equals(model.getSalesReport())) {
                   selectedFile = tempFile;
                   salesReportLbl.setText(selectedFile.getName());
                   importBtn.setDisable(false);
               }
               else {
                   Stage errorStage = new Stage();
                   ViewLoader.showStage(new OSError("This sales report has already been imported!"), "/view/Error.fxml", "ERROR", errorStage);
               }
            }
       }
       catch(Exception e){
           System.out.println("No file chosen.");
       }
   }
   
    @FXML
    private void handleSelectOrderBtn(ActionEvent event){
       try{
           FileChooser fileChooser = new FileChooser();
           fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
           File tempFile = fileChooser.showOpenDialog(new Stage());
           String fileName = tempFile.getName();
           if(tempFile.isFile()){
               selectedOrderFile = tempFile;
               orderLbl.setText(selectedOrderFile.getName());
               importOrderBtn.setDisable(false);
            }
       }
       catch(Exception e){
           System.out.println("No file chosen.");
           System.out.println(e);
       }
   }
   
   @FXML
   private void handleImportBtn(ActionEvent event) throws Exception{
        model.setSalesReport(selectedFile);
        model.importSalesReport(selectedFile);
        importBtn.setDisable(true);
    }
   
    @FXML
    private void handleImportOrderBtn(ActionEvent event) throws Exception {
        model.importOrder(selectedOrderFile);
        importOrderBtn.setDisable(true);
    }
      
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
}
