package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;



public class OrderController extends Controller<Supplier>{
   public final Supplier getSupplier() { return model; }
   public final ObservableList<Product> getList() { return model.getOrder().getList(); }
   
   private boolean allSelected;
   final Clipboard clipboard = Clipboard.getSystemClipboard();
   final ClipboardContent content = new ClipboardContent();
   
   @FXML ObservableList<Product> tempList = FXCollections.observableArrayList();
   @FXML TableView<Product> productTv = new TableView<Product>();
   @FXML Label titleLbl;
   
   @FXML TableColumn<Product, Boolean> exportClm;
   @FXML TableColumn<Product, Boolean> backOrderClm;
   @FXML TableColumn<Product, String> nameClm;
   @FXML TableColumn<Product, String> skuClm;
   @FXML TableColumn<Product, Integer> qtyClm;
   
   @FXML Button makeBackorderBtn;
   @FXML Button adjustQtyBtn;
   @FXML Button deleteProdBtn;
   @FXML Button selectBtn;
   @FXML Button saveNotesBtn;
   @FXML Button clearNotesBtn;
   @FXML Button changeSupplierBtn;
   
      
   @FXML TextField filterTf;
   @FXML TextArea notesTa;
   
   @FXML
    private void initialize() {
        allSelected = false;

        stage.getIcons().add(new Image(OrderController.class.getResourceAsStream("/view/icon.png")));

        this.getList().forEach(p -> tempList.add(p));
        
        FilteredList<Product> filteredProducts = new FilteredList<>(tempList, s -> true);
        
        productTv.setItems(filteredProducts);

        filterTf.textProperty().addListener( obs -> {
            String filter = filterTf.getText();
            if(filter == null || filter.length() == 0){
                filteredProducts.setPredicate(s -> true);
            }
            else{
                filteredProducts.setPredicate(s -> s.SKUProperty().getValue().toLowerCase().contains(filter.toLowerCase()));
            }
        });        
        
        titleLbl.setText(model.getName() + " Order");

        productTv.getSelectionModel().selectedItemProperty().addListener((o, oldProd, newProd) -> {
            makeBackorderBtn.setDisable(newProd == null);
            adjustQtyBtn.setDisable(newProd == null);
            deleteProdBtn.setDisable(newProd == null);
            saveNotesBtn.setDisable(newProd == null);
            clearNotesBtn.setDisable(newProd == null);
            //changeSupplierBtn.setDisable(newProd == null); KEEP DISABLED CAUSE NOT WORKING
            
            if(newProd != null){
                notesTa.setText(newProd.notesProperty().getValue());
            }
        });

        nameClm.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        skuClm.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
        qtyClm.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        backOrderClm.setCellValueFactory(cellData -> cellData.getValue().backorderProperty());
        backOrderClm.setCellFactory(tc -> new CheckBoxTableCell<>());

        exportClm.setCellValueFactory(cellData -> cellData.getValue().exportProperty());
        exportClm.setCellFactory(tc -> new CheckBoxTableCell<>());

        //ON KEYPRESS
        productTv.setOnKeyPressed(e -> {
            //MAKE BACKORDER IF KEYPRESS IS B
            if (!productTv.getSelectionModel().isEmpty()) {
                if (e.getCode() == KeyCode.B) {
                    try {
                        makeBackorder();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }

                //SELECT IF KEYPRESS IS S
                if (e.getCode() == KeyCode.S) {
                    try {
                        selectItem();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                //COPY SKU IF KEYPRESS IS C
                if (e.getCode() == KeyCode.C) {
                    try {
                        content.putString(getSelectedProduct().SKUProperty().getValue());
                        clipboard.setContent(content);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }           
       });

       //SELECT ON DOUBLE CLICK
       productTv.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try{
                        selectItem();
                        productTv.getSelectionModel().clearSelection();
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        });       
   }
    
    @FXML void handleChangeSupplierBtn(ActionEvent event) throws Exception{
        Product prod = getSelectedProduct();
        Stage changeStage = new Stage();
        changeStage.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                tempList.clear();
                getList().remove(prod);
                model.getOrder().getList().forEach(p -> tempList.add(p));                
            }
        });
        ViewLoader.showStage(prod, "/view/ChangeSupplier.fxml", "Change Supplier", changeStage);
    }

    @FXML void handleAddProdBtn(ActionEvent event) throws Exception{
        Stage addStage = new Stage();
        addStage.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                tempList.clear();
                model.getOrder().getList().forEach(p -> tempList.add(p));
            }
        });
        ViewLoader.showStage(model, "/view/AddProduct.fxml", "Add Product", addStage);
    }

   @FXML void handleDeleteProdBtn(ActionEvent event){
       Product p = getSelectedProduct();
       productTv.getSelectionModel().clearSelection();
       tempList.remove(p);
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
       if(p.backorderProperty().getValue()){
           p.setBackorder(false);
       }
       else p.setBackorder(true);
   }
   
    @FXML
    void handleSelectBtn(ActionEvent event) {        
        if (allSelected) {
            for (Product p : productTv.getItems()) {
                p.setExport(false);
            }
        } else {
            for (Product p : productTv.getItems()) {
                p.setExport(true);
            }
        }
        
        allSelected = !allSelected;
        
    }
   
    @FXML
    void handleExportSelectedBtn() throws Exception{

        ObservableList<Product> exportList = FXCollections.observableArrayList();

        for (Product p : productTv.getItems()) {
            if (p.exportProperty().getValue()) {
                exportList.add(p);
            }
        }
        
        if (!exportList.isEmpty()) {
            

            FileChooser fileChooser = new FileChooser();
            
            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            fileChooser.setInitialFileName(model.getName() + " Order" + ".csv");
            //Show save file dialog
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                exportOrder(exportList, file);
                for (Product p : exportList) {
                    this.tempList.remove(p);
                }
            }
        } 
        else {
            Stage errorStage = new Stage();
            ViewLoader.showStage(new OSError("No products selected"), "/view/Error.fxml", "ERROR", errorStage);
        }
        
        
    }
   
   @FXML void handleExportAllBtn() throws Exception{
        
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialFileName(model.getName() + " Order" + ".csv");
        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        
        exportOrder(this.tempList , file);
        
        this.tempList.clear();
   }
   
   @FXML
   private void handleCloseBtn(ActionEvent event){
       stage.close();
   }
   
   @FXML
   private void handleSaveExitBtn(ActionEvent event){
       try{
           this.getList().clear();
           this.tempList.forEach(p -> this.getList().add(p));
           stage.close();
       }
       catch(Exception e){
           System.out.println(e.toString());
           System.out.println("Error writing order.");
       }
   }
   
   @FXML
   private void handleSaveNotesBtn(ActionEvent event){
       Product p = getSelectedProduct();
       p.setNotes(notesTa.getText());
   }
   
   @FXML
   private void handleClearNotesBtn(ActionEvent event){
       notesTa.setText("");
   }
   
    private void exportOrder(ObservableList<Product> list, File f) throws Exception{
        FileWriter orderWriter;
        orderWriter = new FileWriter(f.getPath(), false);
        
        orderWriter.write("Product Name,SKU,Quantity,Backorder\n");
        
        orderWriter.write("\n");
        
        for(Product p : list) {
            if (p.backorderProperty().getValue()) {
                orderWriter.write(p.titleProperty().getValue() + ",");
                orderWriter.write(p.SKUProperty().getValue() + ",");
                orderWriter.write(p.quantityProperty().getValue() + ",");
                orderWriter.write(p.backorderProperty().getValue() + "\n");
            }
        }
        
        orderWriter.write("\n");

        for (Product p : list) {
            if (!p.backorderProperty().getValue()) {
                orderWriter.write(p.titleProperty().getValue() + ",");
                orderWriter.write(p.SKUProperty().getValue() + ",");
                orderWriter.write(p.quantityProperty().getValue() + ",");
                orderWriter.write(p.backorderProperty().getValue() + "\n");
            }
        }

        orderWriter.flush();
        orderWriter.close();
        
    }
    
    private void selectItem() {
        Product p = getSelectedProduct();
        if (p.exportProperty().getValue()) {
            p.setExport(false);
        } else {
            p.setExport(true);
        }
    }
    
    private void makeBackorder() {
        Product p = getSelectedProduct();
        if (p.backorderProperty().getValue()) {
            p.setBackorder(false);
        } else {
            p.setBackorder(true);
        }
    }

    private Product getSelectedProduct() {
        return productTv.getSelectionModel().getSelectedItem();
    }
}
