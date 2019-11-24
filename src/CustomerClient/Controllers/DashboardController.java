package CustomerClient.Controllers;

import Model.Context;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private AnchorPane pizzaOfTheDayPane;
    @FXML private Label ordersDateLabel;
    @FXML private Label nameLabel;
    @FXML private Label pendingOrders;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentUserEmail = Context.getInstance().currentUser().getEmailAddress();
        nameLabel.setText(currentUserEmail);

        fadeIn.setNode(pizzaOfTheDayPane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        fadeIn.playFromStart();

        String [] months = {"0", "January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonthInt = (now.get(Calendar.MONTH) + 1);
        int currentDate = now.get(Calendar.DATE);
        String currentMonth = "";

        for (int i = 0; i < months.length; i++) {
            if (months[currentMonthInt] == months[i]) {
                currentMonth = months[currentMonthInt];
            }
        }

        String currentFullDate = currentDate + " " + currentMonth + " " + currentYear;
        ordersDateLabel.setText(currentFullDate.toUpperCase());

        int userIdFetched = getId(currentUserEmail);
        try {
            // create a mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/Pizzalicious";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            String query = "SELECT count(orderId) AS n FROM pizzaorder WHERE orderBy=? AND status=?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, userIdFetched);
            preparedStmt.setInt(2, 0);

            ResultSet RS = preparedStmt.executeQuery();
            RS.next();
            int number = RS.getInt("n");

            pendingOrders.setText("You have " + number + " pending orders.");

        } catch  (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }// try for database
    }

    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(4000)
    );

    public void showMenuWindow() throws IOException {
        Parent pizzaMenu = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/PizzaMenu.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Pizza Menu");
        stage.setScene(new Scene(pizzaMenu, 1350, 700));
        stage.show();
    }

    public void showOrderWindow() throws IOException {
        Parent pizzaMenu = FXMLLoader.load(getClass().getResource("../FXMLUserInterfaces/OrderPizza.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Order Pizza");
        stage.setScene(new Scene(pizzaMenu, 1350, 700));
        stage.show();
    }

    public int getId(String email) {
        int userId = 0;
        try {
            // create a mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/Pizzalicious";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            String query = "SELECT userId FROM user WHERE email=?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, email);
            ResultSet RS = preparedStmt.executeQuery();
            RS.next();
            userId = RS.getInt("userId");

        } catch  (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }// try for database
        return userId;
    }
}
