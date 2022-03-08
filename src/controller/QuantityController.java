package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import model.*;

public class QuantityController extends Controller<Product> {

    public final Product getProduct() {
        return model;
    }
    
    @FXML private Label titleLbl;
    @FXML private Label currQtyLbl;
    @FXML private TextField qtyTf;
    
    @FXML
    private void initialize() {
        stage.getIcons().add(new Image("file:src/view/icon.png"));
        titleLbl.setText(model.SKUProperty().getValue());
        currQtyLbl.setText(model.quantityProperty().getValue() + "");
        qtyTf.setText(model.quantityProperty().getValue() + "" );
        
        
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
    private void handleSetQtyBtn(){
        int tfValue = Integer.parseInt(qtyTf.getText());
        model.setQty(tfValue);
        stage.close();
    }
    
    @FXML
    private void handleCloseBtn(ActionEvent event) {
        stage.close();
    }
}
