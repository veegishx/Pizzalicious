package ManagerClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerOrderClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLUserInterfaces/ManageOrdersDashboard.fxml"));
        primaryStage.setTitle("Pizza Menu");
        primaryStage.setScene(new Scene(root, 590, 400)); // Size for Registration & login stage
        primaryStage.setResizable(false);
        primaryStage.show();
        //Scene scene = primaryStage.getScene();


    }

    public static void main(String[] args)
    {
        launch(args);
    }
}