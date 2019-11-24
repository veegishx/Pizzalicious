package ManagerClient.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LogInStaffController implements Initializable {
    public static String serverHostName = "localhost";
    public static int serverPortNumber = 59090;
    private Socket connectionSocket;
    public static ObjectInputStream objInStream;
    public static ObjectOutput objOutStream;

    @FXML private AnchorPane logInStaffPane;
    @FXML private TextField staffSignInEmail;
    @FXML private TextField staffSignInPassword;

    public void staffSignIn() throws IOException {
        connect();

        String email = staffSignInEmail.getText();
        String pwd = staffSignInPassword.getText();

        objOutStream.writeUTF("staff_signin");
        objOutStream.flush();

        objOutStream.writeUTF(email+"\t"+pwd);
        objOutStream.flush();

        String svrStatus = (String) objInStream.readUTF();
        if (svrStatus.equals("staff_sign_in_ok")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pizzalicious Staff Sign In Status");
            alert.setHeaderText("Account Successfully signed in.");
            alert.setContentText("Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();
            showMangeOrder();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pizzalicious Staff Sign In Status");
            alert.setHeaderText("Sign In Failed.");
            alert.setContentText("Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();
            showRegistrationForm();
        }
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

    public void showRegistrationForm() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/RegisterStaff.fxml"));
        logInStaffPane.getChildren().setAll(pane);
    }

    public void showMangeOrder() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/ManageOrdersDashboard.fxml"));
        logInStaffPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
