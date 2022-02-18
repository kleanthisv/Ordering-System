/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author klean
 */
public class OSError {
    
    private String errorString;
    
    public OSError(String error){
        this.errorString = error;
    }
    
    @Override
    public String toString(){
        return this.errorString;
    }
}
