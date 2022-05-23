package model;

import javafx.beans.property.*;


public class Supplier {
    
    private Order order;
    private StringProperty name;
    private BooleanProperty isDone;
    
    public Supplier(String name, boolean done){
        this.name = new SimpleStringProperty();
        this.name.set(name.trim());
        this.order = new Order();
        this.isDone = new SimpleBooleanProperty();
        this.isDone.set(done);
    }
        
    public Product getProduct(String SKU){
        
        for(Product p : this.order.getList()){
            if(p.SKUProperty().getValue().toLowerCase().equals(SKU.toLowerCase())) return p;
        }
        return null;
    }
    
    public Order getOrder(){
        return this.order;
    }
    
    public ReadOnlyStringProperty nameProperty(){
        return this.name;
    }
    
    public BooleanProperty doneProperty(){
        return this.isDone;
    }
    
    public void setDone(boolean bool){
        this.isDone.set(bool);
    }
    
    public boolean isDone(){
        return this.isDone.getValue();
    }
    
    public String getName(){
        return this.name.getValue();
    }
    
    @Override
    public String toString(){
        return this.getName() + "," + this.isDone();
    }
    
}
