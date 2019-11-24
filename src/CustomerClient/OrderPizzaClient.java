package CustomerClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrderPizzaClient extends Application {
    /*
    Pizzalicious Pizzeria Client JavaFX start() method
    This is the main menu interface for the client. Since we are using JavaFX, the core logic of the
    client has been defined in the Controller.java class.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLUserInterfaces/OrderPizza.fxml"));
        primaryStage.setTitle("Order Pizza");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
        //Scene scene = primaryStage.getScene();


    }

    public static void main(String[] args)
    {
        launch(args);
    }

}