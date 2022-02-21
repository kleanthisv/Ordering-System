package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order {
    
    private ObservableList<Product> productList = FXCollections.observableArrayList();
    
    private Supplier supplier;
    
    public Order(){
    }
    
    public void addAllProducts(ArrayList<Product> products){
        for(Product s : products){
            this.addProduct(s,1); //CHANGE TO MAKE IT SO QUANTITY IS A DIFFERENT VALUE WHEN RECEIVED AS TO WHEN IT IS STORED
        }
    }
    
    public void addProduct(Product product, int Quantity){
        if(productExists(product)){
            product.orderMore(Quantity);
        }
        else productList.add(product);
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
