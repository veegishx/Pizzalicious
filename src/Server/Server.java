package Server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSetMetaData;
import static java.lang.Integer.parseInt;

import Model.*;

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

                            User userSignUp = (User) inFromClient.readObject();
                            String signUpEmail = userSignUp.getEmailAddress();
                            String signUpAddress = userSignUp.getAddress();
                            String signUpFirstName = userSignUp.getFirstName();
                            String signUpLastName = userSignUp.getLastName();
                            String signUpPhoneNo = userSignUp.getPhoneNo();
                            String signUpPassword = userSignUp.getPassword();
                            try {
                                // create a mysql database connection
                                String myDriver = "org.gjt.mm.mysql.Driver";
                                String myUrl = "jdbc:mysql://localhost:3306/pizzalicious";
                                Class.forName(myDriver);
                                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                                // create a sql date object so we can use it in our INSERT statement

                                // the mysql insert statement
                                String query = " insert into user (firstName, lastName, email, password, address, phoneNo)"
                                        + " values (?, ?, ?, ?, ?, ?)";

                                // create the mysql insert preparedstatement
                                PreparedStatement preparedStmt = conn.prepareStatement(query);
                                preparedStmt.setString(1, signUpFirstName);
                                preparedStmt.setString(2, signUpLastName);
                                preparedStmt.setString(3, signUpEmail);
                                preparedStmt.setString(4, signUpPassword);
                                preparedStmt.setString(5, signUpAddress);
                                preparedStmt.setString(6, signUpPhoneNo);

                                // execute the preparedstatement
                                preparedStmt.execute();
                                outToClient.writeUTF("sign_up_ok");
                                outToClient.flush();

                                conn.close();
                            } catch (Exception e) {
                                outToClient.writeUTF("sign_in_failure");
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            }// try for database

                            break; //user create
                        case "user_signin":
                            //System.out.println("ok");
                            String signInInfo = (String) inFromClient.readUTF();
                            String [] signInInfoArray = signInInfo.split("\t");
                            String signInEmail = signInInfoArray[0];
                            String signInPassword = signInInfoArray[1];
                            System.out.println(signInEmail + " " + signInPassword);
                            try {
                                // create a mysql database connection
                                String myDriver = "org.gjt.mm.mysql.Driver";
                                String myUrl = "jdbc:mysql://localhost:3306/pizzalicious";
                                Class.forName(myDriver);
                                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                                // create a sql date object so we can use it in our INSERT statement

                                // the mysql insert statement
                                String query = "SELECT count(email) AS n FROM user WHERE email=? AND password=?";

                                // create the mysql insert preparedstatement
                                PreparedStatement preparedStmt = conn.prepareStatement(query);
                                preparedStmt.setString(1, signInEmail);
                                preparedStmt.setString(2, signInPassword);

                                // execute the preparedstatement


                                ResultSet RS = preparedStmt.executeQuery();
                                RS.next();
                                String countN = RS.getString("n");
                                int n = parseInt(countN);
                                if (n == 1){
                                    System.out.println("signed in");
                                    outToClient.writeUTF("sign_in_ok");
                                    outToClient.flush();
                                } else {
                                    outToClient.writeUTF("sign_in_failure");
                                    outToClient.flush();
                                    System.out.println("sign in failed");
                                }

                                conn.close();
                            } catch (Exception e) {
                                outToClient.writeUTF("sign_in_failure");
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            }// try for database
                            break;//user_signin
                        case "order_create":
                            PizzaOrder P1 = (PizzaOrder) inFromClient.readObject();
                            int orderid = P1.getOrderId();
                            double orderTotalPrice = P1.getOrderTotalPrice();
                            int orderedBy= P1.getOrderedBy();
                            int staffID =P1.getStaffID();
                            ArrayList<Pizza> orderContent = P1.getOrderContent();
                            String pizzaContent = orderContent.toString();
                            try {
                                // create a mysql database connection
                                String myDriver = "org.gjt.mm.mysql.Driver";
                                String myUrl = "jdbc:mysql://localhost:3306/pizzalicious";
                                Class.forName(myDriver);
                                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                                // create a sql date object so we can use it in our INSERT statement

                                // the mysql insert statement
                                String query = " insert into pizzaorder (orderTotalPrice, orderBy, orderContent,staffId)"
                                        + " values (?, ?, ?, ?)";

                                // create the mysql insert preparedstatement
                                PreparedStatement preparedStmt = conn.prepareStatement(query);
                                preparedStmt.setDouble(1,orderTotalPrice );
                                preparedStmt.setInt(2,orderedBy);
                                preparedStmt.setObject(3, pizzaContent);
                                preparedStmt.setInt(4, staffID);

                                // execute the preparedstatement
                                preparedStmt.execute();

                                conn.close();
                            } catch (Exception e) {
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            }// try for database

                            break;//order create
                        case "staff_signin":
                            //System.out.println("ok");
                            String staff_signInInfo = (String) inFromClient.readUTF();
                            String [] staff_signInInfoArray = staff_signInInfo.split("\t");
                            String staff_signInEmail = staff_signInInfoArray[0];
                            String staff_signInPassword = staff_signInInfoArray[1];
                            //System.out.println(signInEmail + " " + signInPassword);
                            try {
                                // create a mysql database connection
                                String myDriver = "org.gjt.mm.mysql.Driver";
                                String myUrl = "jdbc:mysql://localhost:3306/Pizzalicious";
                                Class.forName(myDriver);
                                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                                // create a sql date object so we can use it in our INSERT statement

                                // the mysql insert statement
                                String query = "SELECT count(email) AS n FROM staff WHERE email=? AND password=?";

                                // create the mysql insert preparedstatement
                                PreparedStatement preparedStmt = conn.prepareStatement(query);
                                preparedStmt.setString(1, staff_signInEmail);
                                preparedStmt.setString(2, staff_signInPassword);

                                // execute the preparedstatement


                                ResultSet RS = preparedStmt.executeQuery();
                                RS.next();
                                String countN = RS.getString("n");
                                int n = parseInt(countN);
                                if (n == 1){
                                    //System.out.println("signed in");
                                    outToClient.writeUTF("staff_sign_in_ok");
                                    outToClient.flush();
                                } else {
                                    outToClient.writeUTF("staff_sign_in_failure");
                                    outToClient.flush();
                                    //System.out.println("sign in failed");
                                }

                                conn.close();
                            } catch (Exception e) {
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            }// try for database
                            break;
                        case "staff_registration":

                            Staff staffRegistration = (Staff) inFromClient.readObject();
                            String staffRegistration_Email = staffRegistration.getEmail();
                            String staffRegistration_FirstName = staffRegistration.getFirstName();
                            String staffRegistration_LastName = staffRegistration.getLastName();
                            String staffRegistration_Password = staffRegistration.getPassword();
                            try {
                                // create a mysql database connection
                                String myDriver = "org.gjt.mm.mysql.Driver";
                                String myUrl = "jdbc:mysql://localhost:3306/Pizzalicious";
                                Class.forName(myDriver);
                                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                                // create a sql date object so we can use it in our INSERT statement

                                // the mysql insert statement
                                String query = " insert into staff (firstName, lastName, email, password)"
                                        + " values (?, ?, ?, ?)";

                                // create the mysql insert preparedstatement
                                PreparedStatement preparedStmt = conn.prepareStatement(query);
                                preparedStmt.setString(1, staffRegistration_FirstName);
                                preparedStmt.setString(2, staffRegistration_LastName);
                                preparedStmt.setString(3, staffRegistration_Email);
                                preparedStmt.setString(4, staffRegistration_Password);

                                // execute the preparedstatement
                                preparedStmt.execute();
                                outToClient.writeUTF("staff_registration_ok");
                                outToClient.flush();
                                conn.close();
                            } catch (Exception e) {
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            }// try for database

                            break;
                        case "read_orders":
                            try {
                                // create a mysql database connection
                                String myDriver = "org.gjt.mm.mysql.Driver";
                                String myUrl = "jdbc:mysql://localhost:3306/Pizzalicious";
                                Class.forName(myDriver);
                                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                                // create a sql date object so we can use it in our INSERT statement

                                // the mysql insert statement
                                String query = " SELECT * FROM pizzaorder";

                                // create the mysql insert preparedstatement
                                PreparedStatement preparedStmt = conn.prepareStatement(query);

                                ResultSet resultSet = preparedStmt.executeQuery();
                                int orderIdResult, orderByResult, staffId;
                                double orderTotalPriceResult ;
                                Object orderObject;
                                ArrayList<PizzaOrder> results = new ArrayList<PizzaOrder>();
                                PizzaOrder order;
                                while (resultSet.next()) {
                                    orderIdResult = resultSet.getInt("orderId");
                                    orderTotalPriceResult = resultSet.getDouble("orderTotalPrice");
                                    orderByResult = resultSet.getInt("orderBy");
                                    orderObject = resultSet.getObject(4);
                                    staffId = resultSet.getInt("staffId");
                                    order = new PizzaOrder(orderIdResult, orderTotalPriceResult, staffId, orderByResult, orderObject);
                                    results.add(order);
                                }

                                outToClient.writeObject(results);
                                outToClient.flush();

                                System.out.println(results.size());

                                outToClient.writeUTF("orders_ok");
                                outToClient.flush();
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
