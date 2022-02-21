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
