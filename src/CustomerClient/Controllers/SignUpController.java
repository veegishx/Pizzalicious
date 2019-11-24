package CustomerClient.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML private AnchorPane rootSignUpPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void customerSignUp() {

    }

    public void showTos() throws IOException {
    }

    public void showPolicy() {

    }

    public void showLoginForm() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/SignIn.fxml"));
        rootSignUpPane.getChildren().setAll(pane);
    }
}
