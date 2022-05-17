package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import java.io.File;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

public class SessionController extends Controller<Session> {

    public final Session getSession() {
        return model;
    }

    public final ObservableList<Supplier> getList() {
        return model.getSuppliers().getList();
    }

    private String version = "v1.3.4";
    
    @FXML TableColumn<Supplier, Boolean> doneClm;
    @FXML TableColumn<Supplier, String> titleClm;
    
    @FXML private Label versionLbl;
    @FXML private TextField filterTf;
    @FXML private ListView suppliersLv;
    @FXML private TableView suppliersTv;
    @FXML private Label reportDateLbl;
    @FXML private Button openOrderBtn;
    @FXML private Button deleteSupplierBtn;
    @FXML private Button addSupplierBtn;
    @FXML private ImageView logo;
    
    @FXML
    private void initialize() {
                
        FilteredList<Supplier> filteredSuppliers = new FilteredList<>(getList(), s -> true);
        
        suppliersTv.setItems(filteredSuppliers);
        
        titleClm.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        doneClm.setCellValueFactory(cellData -> cellData.getValue().doneProperty());
        
        filterTf.textProperty().addListener( obs -> {
            String filter = filterTf.getText();
            if(filter == null || filter.length() == 0){
                filteredSuppliers.setPredicate(s -> true);
            }
            else{
                filteredSuppliers.setPredicate(s -> s.getName().toLowerCase().contains(filter.toLowerCase()));
            }
        });
        
        suppliersTv.getSelectionModel().selectedItemProperty().addListener((o, oldAcct, newAcct) -> {
            openOrderBtn.setDisable(newAcct == null);
            deleteSupplierBtn.setDisable(newAcct == null);
        });
        
        reportDateLbl.textProperty().bind(model.getReportDate());
        logo.setImage(new Image(SessionController.class.getResourceAsStream("/view/logo.png")));
        versionLbl.setText(version);
        
        //Open supplier on double click
        suppliersTv.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try{
                        openOrder();
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        });
    }

    @FXML
    private void handleAddSupplierBtn(ActionEvent event) throws Exception {
        Stage orderStage = new Stage();
        ViewLoader.showStage(model, "/view/AddSupplier.fxml", "Add Supplier", orderStage);
    }
    
    @FXML
    private void handleDeleteSupplierBtn(ActionEvent event) throws Exception {
        Supplier p = getSelectedSupplier();
        model.getSuppliers().getList().remove(p);
    }

    @FXML
    private void handleImportSalesReportBtn(ActionEvent event) throws Exception {
        Stage importStage = new Stage();
        ViewLoader.showStage(model, "/view/Import.fxml", "Import Sales Report", importStage);
    }

    @FXML
    private void handleOpenOrderBtn(ActionEvent event) throws Exception {
        Supplier s = getSelectedSupplier();
        Stage orderStage = new Stage();
        ViewLoader.showStage(s, "/view/Order.fxml", s.getName() + " Order", orderStage);
    }
    
    @FXML
    private void handleSaveBtn(ActionEvent event) throws Exception {
        model.writeSuppliers();
        for(Supplier s : getList()){
            model.writeOrderCSV(s);
        }
    }
    
    @FXML
    private void handleSaveExitBtn(ActionEvent event) throws Exception {
        model.writeSuppliers();
        for (Supplier s : getList()) {
            model.writeOrderCSV(s);
        }
        this.stage.close();
    }

    private Supplier getSelectedSupplier() {
        return (Supplier) suppliersTv.getSelectionModel().getSelectedItem();
    }
    
    private void openOrder() throws Exception{
        Supplier s = getSelectedSupplier();
        Stage orderStage = new Stage();
        ViewLoader.showStage(s, "/view/Order.fxml", s.getName() + " Order", orderStage);
    }
}
