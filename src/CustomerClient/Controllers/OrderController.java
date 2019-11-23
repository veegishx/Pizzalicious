package CustomerClient.Controllers;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class OrderController implements Initializable {
    @FXML private ComboBox<String> orderTypeDropdown;
    @FXML private ComboBox<String> orderSizeDropdown;
    @FXML private TextField orderQuantityTxt;
    @FXML private ListView<VBox> ordersListView;
    @FXML private Label itemsInOrderQueue;

    public void initialize(URL location, ResourceBundle resources) {
        orderTypeDropdown.getItems().removeAll(orderTypeDropdown.getItems());
        orderTypeDropdown.getItems().addAll("Hawaiian Craze", "Spicy Bacon Deluxe", "Spicy 3 Cheese", "Mushroom Cream Light");
        orderTypeDropdown.getSelectionModel().select("Hawaiian Craze");

        orderSizeDropdown.getItems().removeAll(orderSizeDropdown.getItems());
        orderSizeDropdown.getItems().addAll("Regular", "Medium", "Large");
        orderSizeDropdown.getSelectionModel().select("Regular");

        orderQuantityTxt.setText("1");
    }

    public void viewMenu() throws IOException {
        Parent pizzaMenu = FXMLLoader.load(getClass().getResource("FXMLUserInterfaces/PizzaMenu.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Pizza Menu");
        stage.setScene(new Scene(pizzaMenu, 1350, 700));
        stage.show();
    }

    public void updateOrder() throws FileNotFoundException {
        // Get order values
        String orderType = orderTypeDropdown.getValue();
        String orderTypeLabel = orderTypeDropdown.getValue();
        String orderSizeLabel = "Pizza Size: " + orderSizeDropdown.getValue();
        String orderQuantityLabel = "Ordered Quantity: " + orderQuantityTxt.getText();

        // Set Order Values
        Label orderListViewNo = new Label("Item #" + (ordersListView.getItems().size() + 1));
        Label orderListViewTitle = new Label(orderTypeLabel);
        Label orderListViewQuantity = new Label(String.valueOf(orderQuantityLabel));
        Label orderListViewSize = new Label(orderSizeLabel);

        ImageView orderListViewThumbnail;
        switch (orderType) {
            case "Hawaiian Craze":
                orderListViewThumbnail = new ImageView(new Image("CustomerClient/FXMLUserInterfaces/4.png"));
                break;
            case "Spicy Bacon Deluxe":
                orderListViewThumbnail = new ImageView(new Image("CustomerClient/FXMLUserInterfaces/6.png"));
                break;
            case "Spicy 3 Cheese":
                orderListViewThumbnail = new ImageView(new Image("CustomerClient/FXMLUserInterfaces/3.png"));
                break;
            default:
                orderListViewThumbnail = new ImageView(new Image("CustomerClient/FXMLUserInterfaces/5.png"));
                break;
        }

        orderListViewNo.setTextFill(Color.web("#FFFFFF"));
        orderListViewNo.setFont(new Font("Dubai", 14));

        orderListViewTitle.setTextFill(Color.web("#0076a3"));
        orderListViewQuantity.setTextFill(Color.web("#0076a3"));
        orderListViewSize.setTextFill(Color.web("#0076a3"));

        orderListViewThumbnail.setFitHeight(100);
        orderListViewThumbnail.setFitWidth(100);
        orderListViewThumbnail.preserveRatioProperty();

        VBox verticalNode = new VBox(orderListViewThumbnail, orderListViewTitle, orderListViewQuantity, orderListViewSize, orderListViewNo);
        verticalNode.setAlignment(Pos.BASELINE_CENTER);
        verticalNode.setPadding(new Insets(20 ,10 , 0, 10));
        ordersListView.setOrientation(Orientation.HORIZONTAL);
        ordersListView.getItems().add(verticalNode);
        ordersListView.setStyle("-fx-background-color:  #202124;");

        itemsInOrderQueue.setText(String.valueOf(ordersListView.getItems().size()));
    }

    public void clearOrderList() {
        ordersListView.getItems().clear();
        itemsInOrderQueue.setText("0");
    }

    public void confirmOrderList() {

    }

}
