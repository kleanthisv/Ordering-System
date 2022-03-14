package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import model.*;

public class AddSupplierController extends Controller<Session> {

    public final Session getSession() {
        return model;
    }
    
    @FXML private TextField nameTf;
    
    @FXML
    private void initialize() {
        stage.getIcons().add(new Image(AddProductController.class.getResourceAsStream("/view/icon.png")));
        
    }
    
    @FXML
    private void handleAddSupplierBtn() {
        String supplierName = nameTf.getText();

        if (model.getSuppliers().getSupplier(supplierName) == null && !supplierName.isEmpty()) {
            model.getSuppliers().add(new Supplier(supplierName));
            System.out.println("Supplier " + supplierName + " added to supplier list.");
        }
        stage.close();
    }

    @FXML
    private void handleCloseBtn(ActionEvent event) {
        stage.close();
    }
}
