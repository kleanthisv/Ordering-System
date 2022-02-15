package model;

import java.util.ArrayList;

public class Suppliers {
    
    private ArrayList<Supplier> suppliers;
    
    public Suppliers(){
        this.suppliers = new ArrayList<Supplier>();
    }
    
    public void add(Supplier s){
        if(!s.getName().isEmpty()){
            this.suppliers.add(s);
        }
    }
    
    public ArrayList<Supplier> getSuppliers(){
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
    
    public boolean hasSupplier(String name){
        for(Supplier s : suppliers){
            if(s.getName().equals(name)) return true;
        }
        return false;
    }
    
}
