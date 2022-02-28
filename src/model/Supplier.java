package model;

import javafx.beans.property.*;


public class Supplier {
    
    private Order order;
    private StringProperty name;
    
    public Supplier(String name){
        this.name = new SimpleStringProperty();
        this.name.set(name.trim());
        this.order = new Order();
    }
    
    public boolean hasProduct(String SKU){
        for(Product p : this.order.getList()){
            if(p.SKUProperty().getValue().matches(SKU)) return true;
        }
        return false;
    }
    
    public Product getProduct(String SKU){
        for(Product p : this.order.getList()){
            if(p.SKUProperty().getValue().matches(SKU)) return p;
        }
        return null;
    }
    
    public Order getOrder(){
        return this.order;
    }
    
    public ReadOnlyStringProperty nameProperty(){
        return this.name;
    }
    
    public String getName(){
        return this.name.getValue();
    }
    
    @Override
    public String toString(){
        return this.getName();
    }
    
}
