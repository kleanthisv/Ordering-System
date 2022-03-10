package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order {
    
    private ObservableList<Product> productList;
    
    private Supplier supplier;
    
    public Order(){
        this.productList = FXCollections.observableArrayList();
    }
        
    public void addProduct(Product product){
        productList.add(product);
    }
    
    public void deleteProduct(Product p){
        productList.remove(p);
    }
    
    private boolean productExists(Product product){
        for(Product p : productList){
            if(p.equals(product)){
                return true;
            }
        }
        return false;
    }
    
    public ObservableList<Product> getList(){
        return this.productList;
    }
    
}
