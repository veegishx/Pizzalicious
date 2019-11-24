package ManagerClient.Controllers;


import Model.Pizza;
import Model.PizzaOrder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class OrderController implements Initializable {
    public static String serverHostName = "localhost";
    public static int serverPortNumber = 59090;
    private Socket connectionSocket;
    public static ObjectInputStream objInStream;
    public static ObjectOutput objOutStream;
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

    public void ordersRefresh() throws IOException, ClassNotFoundException {
        connect();
        objOutStream.writeUTF("read_orders");
        objOutStream.flush();

        ArrayList results = (ArrayList) objInStream.readObject();
        System.out.println(results);
    }
}