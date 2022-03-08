package ordering.system;

import au.edu.uts.ap.javafx.*;
import javafx.stage.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
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
        stage.getIcons().add(new Image(OrderingSystem.class.getResourceAsStream("/view/icon.png")));
        ViewLoader.showStage(new Session(), "/view/Session.fxml", "MX5Mania Ordering System", stage);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
}
