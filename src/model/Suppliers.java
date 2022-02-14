/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author klean
 */
public class Suppliers {
    
    ArrayList<Supplier> suppliers;
    
    public Suppliers(){
        this.suppliers = new ArrayList<Supplier>();
        suppliers.add(new Supplier("Maruha Motors"));
    }
    
}
