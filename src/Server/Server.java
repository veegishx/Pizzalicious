package Server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import Model.User;

public class Server implements Runnable {
    public static Socket connetionSocket;
    public static ObjectOutputStream outToClient;
    public static ObjectInputStream inFromClient;

    public void run() {

        try {
            ServerSocket listeningSocket = null;
            listeningSocket = new ServerSocket(59090);
            System.out.println("Server is up & running on port: " + listeningSocket.getLocalPort());

            while (true) {
                try {
                    connetionSocket = listeningSocket.accept();
                    System.out.println("CLIENT CONNECTED");

                    outToClient = new ObjectOutputStream(connetionSocket.getOutputStream()); // get the output stream of client.
                    inFromClient = new ObjectInputStream(connetionSocket.getInputStream());

                    String action = (String) inFromClient.readUTF();
                    switch (action) {
                        case "user_create":

                            User U1 = (User) inFromClient.readObject();
                            String email = U1.getEmailAddress();
                            String address = U1.getAddress();
                            String firstName = U1.getFirstName();
                            String lastName = U1.getLastName();
                            String phoneNo = U1.getPhoneNo();
                            String password = U1.getPassword();
                            try {
                                // create a mysql database connection
                                String myDriver = "org.gjt.mm.mysql.Driver";
                                String myUrl = "jdbc:mysql://localhost/Pizzalicious";
                                Class.forName(myDriver);
                                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                                // create a sql date object so we can use it in our INSERT statement

                                // the mysql insert statement
                                String query = " insert into user (firstName, lastName, email, password, address, phoneNo)"
                                        + " values (?, ?, ?, ?, ?, ?)";

                                // create the mysql insert preparedstatement
                                PreparedStatement preparedStmt = conn.prepareStatement(query);
                                preparedStmt.setString(1, firstName);
                                preparedStmt.setString(2, lastName);
                                preparedStmt.setString(3, email);
                                preparedStmt.setString(4, password);
                                preparedStmt.setString(5, address);
                                preparedStmt.setString(6, phoneNo);

                                // execute the preparedstatement
                                preparedStmt.execute();

                                conn.close();
                            } catch (Exception e) {
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            }// try for database

                            break;
                    }// switch
                } catch (IOException i) {

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } // end of while
        }catch (IOException e){

        } //
    }



    public static void main(String[] args) {
        Thread PizzaServer = new Thread(new Server());
        PizzaServer.start();
        System.out.println("Multi-threaded Server starting...");
    }
}
