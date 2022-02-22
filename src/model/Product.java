/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author klean
 */
public class Product {
    
    private Supplier supplier;
    private IntegerProperty quantity;
    private StringProperty SKU;
    private StringProperty title;
    private BooleanProperty isBackorder;
    
    public Product(String title, String SKU, int qty){
        // if product exists in supplier.getOrder, then add the qty to the order instead of the whole product.        
        this.SKU = new SimpleStringProperty();
        this.SKU.set(SKU);
        
        this.quantity = new SimpleIntegerProperty();
        this.quantity.set(qty);
        
        this.title = new SimpleStringProperty();  
        this.title.set(title);  
        
        this.isBackorder = new SimpleBooleanProperty();
        this.isBackorder.set(false);
    }
    
    public ObservableValue<Integer> quantityProperty(){
        return this.quantity.asObject();
    }
    
    public ReadOnlyStringProperty titleProperty(){
        return this.title;
    }
    
    public ReadOnlyStringProperty SKUProperty(){
        return this.SKU;
    }
    
    public BooleanProperty backorderProperty(){
        return this.isBackorder;
    }
    
    public void setBackorder(boolean backorderStatus){
        this.isBackorder.set(backorderStatus);
        //make the product backorder
    }
    
    public void orderMore(int qty){
        this.quantity.set(this.quantity.getValue() + qty);
    }
    
}
