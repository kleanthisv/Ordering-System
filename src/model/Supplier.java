package model;

import javafx.beans.property.*;


public class Supplier {
    
    private StringProperty name;
    
    public Supplier(String name){
        this.name = new SimpleStringProperty();
        this.name.set(name.trim());
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
