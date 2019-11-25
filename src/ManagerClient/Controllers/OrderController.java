package ManagerClient.Controllers;


import Model.PizzaOrder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public static String serverHostName = "localhost";
    public static int serverPortNumber = 59090;
    private Socket connectionSocket;
    public static ObjectInputStream objInStream;
    public static ObjectOutput objOutStream;
    @FXML private ListView<String> listView;
    @FXML private AnchorPane ordersPane;
    @FXML private Button deleteBtn;
    public String globalOrderId;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ordersRefresh();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("ListView selection changed from oldValue = "
                        + oldValue + " to newValue = " + newValue);
                try {
                    globalOrderId = newValue.split("\t")[0];
                    customerDetails(Integer.parseInt(globalOrderId));
                } catch (Exception e) {
                    System.out.println("Selected Order has been deleted!");
                }
                System.out.println("Currently Selected Order: " + globalOrderId);
            }
        });
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

    public void customerDetails(int id) throws IOException {
        connect();
        objOutStream.writeUTF("read_orders_ID");
        objOutStream.flush();

        objOutStream.writeInt(Integer.parseInt(globalOrderId));
        objOutStream.flush();
/*
        Label details = new Label((String)objInStream.readUTF());
        ordersPane.getChildren().add(details);
*/
        String roundTrip = objInStream.readUTF();
        byte[] bytes= roundTrip.getBytes("cp1252");
        String roundTrip2 = new String(bytes, "cp1251");
        System.out.println(roundTrip2);

        Label details = new Label(roundTrip2);
        details.setWrapText(true);
        details.setMaxWidth(400);
        ordersPane.getChildren().clear();
        ordersPane.getChildren().add(details);


    }


    public void ordersRefresh() throws IOException, ClassNotFoundException {
        connect();
        listView.getItems().clear();
        objOutStream.writeUTF("read_orders");
        objOutStream.flush();

        ArrayList<PizzaOrder> results = (ArrayList<PizzaOrder>) objInStream.readObject();
        String status = "";
        for (PizzaOrder result : results) {
            if (result.getStatus() == 0) {
                status = "Pending";
            } else if (result.getStatus() == 1) {
                status = "Preparing";
            } else {
                status = "Delivered";
            }
            listView.getItems().add(result.getOrderId() + "\t" + getEmail(result.getOrderedBy()) + "\t" + result.getOrderTotalPrice() + "\t" + getAddress(result.getOrderedBy()) + status);
            listView.setStyle("-fx-background-color:  #202124;");
        }
    }

    public String getEmail(int userId) {
        String userEmail = "";
        try {
            // create a mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/Pizzalicious";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            String query = "SELECT email FROM user WHERE userId=?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, userId);
            ResultSet RS = preparedStmt.executeQuery();
            RS.next();
            userEmail = RS.getString("email");

        } catch  (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }// try for database
        return userEmail;
    }

    public String getAddress(int userId) {
        try {
            // create a mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/Pizzalicious";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            String query = "SELECT address FROM user WHERE userId=?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, userId);
            ResultSet RS = preparedStmt.executeQuery();
            RS.next();

        } catch  (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }// try for database
        return "";
    }

    public void deleteOrder() throws IOException, ClassNotFoundException {
        connect();
        objOutStream.writeUTF("delete_orders");

        objOutStream.flush();

        objOutStream.writeInt(Integer.parseInt(globalOrderId));
        objOutStream.flush();

        ordersRefresh();
    }

    public void setToPreparing() throws IOException, ClassNotFoundException {
        connect();

        objOutStream.writeUTF("update_orders");
        objOutStream.flush();

        objOutStream.writeInt(Integer.parseInt(globalOrderId));
        objOutStream.flush();

        objOutStream.writeUTF("preparing");
        objOutStream.flush();

        ordersRefresh();
    }

    public void setToDelivered() throws IOException, ClassNotFoundException {
        connect();
        objOutStream.writeUTF("update_orders");
        objOutStream.flush();

        objOutStream.writeInt(Integer.parseInt(globalOrderId));
        objOutStream.flush();

        objOutStream.writeUTF("delivered");
        objOutStream.flush();

        ordersRefresh();
    }
}