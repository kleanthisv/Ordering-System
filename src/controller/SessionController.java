package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.*;



public class SessionController extends Controller<Session>{
   public final Session getSession() { return model; }
    
   @FXML private void handleExit(ActionEvent event) throws Exception {
       this.stage.close();
   }
}
