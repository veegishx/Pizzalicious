package CustomerClient.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML private AnchorPane rootSignInPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void customerSignUp() {

    }

    public void showRegistrationForm() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/SignUp.fxml"));
        rootSignInPane.getChildren().setAll(pane);
    }
}
