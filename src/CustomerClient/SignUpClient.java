package CustomerClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUpClient extends Application {
    /*
    Pizzalicious Pizzeria Client JavaFX start() method
    This is the Sign Up interface for the client. Since we are using JavaFX, the core logic of the
    client has been defined in the SignUpController.java class.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLUserInterfaces/SignUp.fxml"));
        primaryStage.setTitle("Pizza Menu");
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