package CustomerClient.Controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DashboardController implements Initializable {
    @FXML private AnchorPane pizzaOfTheDayPane;
    @FXML private Label ordersDateLabel;
    @FXML private Label nameLabel;

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
}
