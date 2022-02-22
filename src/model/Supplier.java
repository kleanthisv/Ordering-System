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
    
    public void writeOrder() throws Exception{
        //write order to csv
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
