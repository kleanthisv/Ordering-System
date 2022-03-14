package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Order {
    
    private ObservableList<Product> productList;
    
    private Supplier supplier;
    
    public Order(){
        this.productList = FXCollections.observableArrayList();
    }
        
    public void addProduct(Product product){
        FilteredList<Product> filteredList = new FilteredList<>(getList(), s -> s.SKUProperty().getValue().toLowerCase().equals(product.SKUProperty().getValue().toLowerCase()));
        if(!filteredList.isEmpty() && filteredList.size() == 1){
            Product p = filteredList.get(0);
            p.setQty(p.quantityProperty().getValue() + product.quantityProperty().getValue());
        }
        else{
            productList.add(product);
        }
    }
    
    public void deleteProduct(Product p){
        productList.remove(p);
    }
    
    public ObservableList<Product> getList(){
        return this.productList;
    }
    
}
