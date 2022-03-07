package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;

public class AddProductController extends Controller<Supplier> {

    public final Supplier getSupplier() {
        return model;
    }
    
    @FXML private TextField nameTf;
    @FXML private TextField skuTf;
    @FXML private TextField qtyTf;
    
    @FXML
    private void initialize() {
        
        qtyTf.setText("0");
        
        qtyTf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    qtyTf.setText(oldValue);
                }
            }
        });
    }
    
    @FXML
    private void handlePlusBtn() {
        int tfValue = Integer.parseInt(qtyTf.getText()) + 1;
        qtyTf.setText(tfValue + "");
    }
    
    @FXML
    private void handleMinusBtn() {
        int tfValue = Integer.parseInt(qtyTf.getText()) - 1;
        qtyTf.setText(tfValue + "");
    }
    
    @FXML
    private void handAddProdBtn(){
        model.getOrder().addProduct(new Product(nameTf.getText(),skuTf.getText(),Integer.parseInt(qtyTf.getText())));
        stage.close();
    }
    
    @FXML
    private void handleCloseBtn(ActionEvent event) {
        stage.close();
    }
}
