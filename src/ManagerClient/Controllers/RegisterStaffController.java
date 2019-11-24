package ManagerClient.Controllers;

import Model.Staff;
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

public class RegisterStaffController implements Initializable {
    public static String serverHostName = "localhost";
    public static int serverPortNumber = 59090;
    private Socket connectionSocket;
    public static ObjectInputStream objInStream;
    public static ObjectOutput objOutStream;

    @FXML private AnchorPane registerStaffPane;
    @FXML private TextField staffRegistrationFName;
    @FXML private TextField staffRegistrationLName;
    @FXML private TextField staffRegistrationEmail;
    @FXML private TextField staffRegistrationPassword;


    public void staffRegistration() throws IOException {
        connect();
        String staff_fname = staffRegistrationFName.getText();
        String staff_lname = staffRegistrationLName.getText();
        String staff_email = staffRegistrationEmail.getText();
        String staff_pwd = staffRegistrationPassword.getText();

        Staff staff = new Staff(staff_fname,staff_lname,staff_email,staff_pwd);

        objOutStream.writeUTF("staff_registration");
        objOutStream.flush();

        objOutStream.writeObject(staff);
        objOutStream.flush();

        String svrStatus = (String) objInStream.readUTF();
        if (svrStatus.equals("staff_registration_ok")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pizzalicious Staff Registration Status");
            alert.setHeaderText("Account Successfully Registered.");
            alert.setContentText("Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();
            showLoginForm();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pizzalicious Staff Sign In Status");
            alert.setHeaderText("Registration Failed.");
            alert.setContentText("Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();

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

    public void showLoginForm() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/LoginStaff.fxml"));
        registerStaffPane.getChildren().setAll(pane);
    }

    public void showMangeOrder() throws IOException {
        //code to show dashboard
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
