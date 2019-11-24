package CustomerClient.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import Model.User;

public class SignUpController implements Initializable {
    @FXML private AnchorPane rootSignUpPane;
    @FXML private TextField signUpEmail;
    @FXML private TextField signUpAddress;
    @FXML private TextField signUpFname;
    @FXML private TextField signUpPhone;
    @FXML private TextField signUpLname;
    @FXML private PasswordField signUpPassword;
    public static String serverHostName = "localhost";
    public static int serverPortNumber = 59090;
    private Socket connectionSocket;
    public static ObjectInputStream objInStream;
    public static ObjectOutput objOutStream;

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


    public void customerSignUp() throws IOException {
        connect();
        String email = signUpEmail.getText();
        String address = signUpAddress.getText();
        String firstName = signUpFname.getText();
        String lastName = signUpLname.getText();
        String phoneNo = signUpPhone.getText();
        String password = signUpPassword.getText();

        User U1 = new User(firstName, lastName, password, email, address, phoneNo);

        objOutStream.writeUTF("user_create");
        objOutStream.flush();

        objOutStream.writeObject(U1);
        objOutStream.flush();

        String svrStatus = (String) objInStream.readUTF();
        if (svrStatus.equals("sign_up_ok")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pizzalicious User Sign Up Status");
            alert.setHeaderText("Account Successfully Created");
            alert.setContentText("You can now Sign in. Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();
            showLoginForm();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pizzalicious User Sign Up Status");
            alert.setHeaderText("Account Creation failed.");
            alert.setContentText("Please try. Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();
        }
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
