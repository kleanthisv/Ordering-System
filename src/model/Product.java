/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.beans.property.*;

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
    
    public Product(String title, String SKU, Supplier supplier, int qty){
        // if product exists in supplier.getOrder, then add the qty to the order instead of the whole product.
        this.supplier = supplier;
        
        this.SKU = new SimpleStringProperty();
        this.SKU.set(SKU);
        
        this.quantity = new SimpleIntegerProperty();
        this.quantity.set(qty);
        
        this.title = new SimpleStringProperty();  
        this.title.set(title);  
        
        this.isBackorder = new SimpleBooleanProperty();
        this.isBackorder.set(false);
    }
    
    public ReadOnlyIntegerProperty quantityProperty(){
        return this.quantity;
    }
    
    public ReadOnlyStringProperty titleProperty(){
        return this.title;
    }
    
    public ReadOnlyStringProperty SKUProperty(){
        return this.SKU;
    }
    
    public void setBackorder(boolean backorderStatus){
        this.isBackorder.set(true);
        //make the product backorder
    }
    
    public void orderMore(int qty){
        this.quantity.set(this.quantity.getValue() + qty);
    }
    
}
