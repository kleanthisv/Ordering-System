/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author klean
 */
public class Supplier {
    
    private String name;
    
    public Supplier(String name){
        this.name = name.trim();
    }
    
    public String getName(){
        return this.name;
    }
    
}
