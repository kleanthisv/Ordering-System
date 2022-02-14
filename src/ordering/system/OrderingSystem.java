package ordering.system;

import au.edu.uts.ap.javafx.*;
import javafx.stage.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import model.*;

public class OrderingSystem extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.centerOnScreen();
        
        Suppliers suppliers = new Suppliers();
        ViewLoader.showStage(suppliers, "/view/Session.fxml", "MX5Mania Ordering System", stage);
        
    }
}