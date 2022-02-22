package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Suppliers {
    
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    
    public Suppliers(){
    }
    
    public void add(Supplier s){
        suppliers.add(s);
    }
    
    public final ObservableList<Supplier> getSuppliers(){
        return this.suppliers;
    }
    
    public Supplier getSupplier(String sName){
        for(Supplier s : suppliers){
            if(s.getName().matches(sName)) return s;
        }
        return null;
    }
    
    public ArrayList<Supplier> getSuppliersAsArr(){
        ArrayList<Supplier> suppliersAL = new ArrayList();
        for(Supplier s : this.suppliers){
            suppliersAL.add(s);
        }
        return suppliersAL;
    }
    
    public void printSuppliers(){
        int x = 0;
        for(Supplier s : suppliers){
            x++;
            System.out.println(s.getName());
        }
        System.out.println(suppliers.size() + " Suppliers total");
    }
    
    public boolean hasSupplier(String sName){
        for(Supplier s : suppliers){
            if(s.getName().matches(sName)) return true;
        }
        return false;
    }
    
}
