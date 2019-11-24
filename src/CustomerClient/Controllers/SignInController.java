package CustomerClient.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.prefs.Preferences;

public class SignInController implements Initializable {
    public static String serverHostName = "localhost";
    public static int serverPortNumber = 59090;
    private Socket connectionSocket;
    public static ObjectInputStream objInStream;
    public static ObjectOutput objOutStream;
    private Preferences prefs;

    @FXML private AnchorPane rootSignInPane;
    @FXML private TextField signInEmail;
    @FXML private TextField signInPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void connect() {
        try {
            this.connectionSocket = new Socket(serverHostName, serverPortNumber);
            InputStream responseFromServer = this.connectionSocket.getInputStream();
            objInStream = new ObjectInputStream(responseFromServer);
            OutputStream requestToServer = this.connectionSocket.getOutputStream();
            objOutStream = new ObjectOutputStream(requestToServer);
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void customerSignIn() throws IOException {
        connect();
        String email = signInEmail.getText();
        String pwd = signInPassword.getText();
        objOutStream.writeUTF("user_signin");
        objOutStream.flush();
        objOutStream.writeUTF(email+"\t"+pwd);
        objOutStream.flush();
        String svrStatus = (String) objInStream.readUTF();
        if (svrStatus.equals("sign_in_ok")){
            Context.getInstance().currentUser().setEmailAddress(email);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pizzalicious User Sign In Status");
            alert.setHeaderText("Account Successfully signed in.");
            alert.setContentText("Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();
            showDashboard();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pizzalicious User Sign In Status");
            alert.setHeaderText("ERROR: Failed to sign in. Please check your email or password and try again.");
            alert.setContentText("Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    public void showDashboard() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/Dashboard.fxml"));
        rootSignInPane.getChildren().setAll(pane);
    }

    public void showRegistrationForm() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/SignUp.fxml"));
        rootSignInPane.getChildren().setAll(pane);
    }
}
