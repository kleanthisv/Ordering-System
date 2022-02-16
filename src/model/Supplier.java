/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.*;

/**
 *
 * @author klean
 */
public class Supplier {
    
    private StringProperty name;
    
    public Supplier(String name){
        this.name.set(name.trim());
    }
    
    public ReadOnlyStringProperty nameProperty(){
        return this.name;
    }
    
    public String getName(){
        return this.name.get();
    }
    
}
