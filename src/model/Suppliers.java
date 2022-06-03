package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Suppliers {
    
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    
    public Suppliers(){
    }
    
    public void add(Supplier s){
        suppliers.add(s);
    }
    
    public final ObservableList<Supplier> getList(){
        return this.suppliers;
    }
    
    public void printSuppliers(){
        int x = 0;
        for(Supplier s : suppliers){
            x++;
            System.out.println(s.getName());
        }
        System.out.println(suppliers.size() + " Suppliers total");
    }
    
    public Supplier getSupplier(String sName){
        FilteredList<Supplier> filteredList = new FilteredList<>(getList(), s -> s.getName().toLowerCase().equals(sName.toLowerCase()));
        if(!filteredList.isEmpty() && filteredList.size() == 1){
            Supplier s = filteredList.get(0);
            return s;
        }
        
        return null;
    }

    void addSupplier(Supplier supplier) {
        this.add(supplier);
    }
    
    void setSuppliers(ObservableList<Supplier> newSuppliers){
        this.suppliers = newSuppliers;
    }
    
}
