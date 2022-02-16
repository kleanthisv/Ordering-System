package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Suppliers {
    
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    
    public Suppliers(){
    }
    
    public void add(Supplier s){
        if(!s.getName().isEmpty()){
            this.suppliers.add(s);
        }
    }
    
    public final ObservableList<Supplier> getSuppliers(){
        return this.suppliers;
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
    
    public boolean hasSupplier(String name){
        for(Supplier s : suppliers){
            if(s.getName().equals(name)) return true;
        }
        return false;
    }
    
}
