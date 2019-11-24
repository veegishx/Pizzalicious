package Model;

import java.io.Serializable;

public class Pizza implements Serializable {
    private String pizzaType;
    private int pizzaPrice;
    private int pizzaQty;
    private int pizzaSize;

    public Pizza() {
    }

    public Pizza(String pizzaType, int pizzaPrice, int pizzaQty, int pizzaSize) {
        this.pizzaType = pizzaType;
        this.pizzaPrice = pizzaPrice;
        this.pizzaQty = pizzaQty;
        this.pizzaSize = pizzaSize;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public int getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(int pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    public int getPizzaQty() {
        return pizzaQty;
    }

    public void setPizzaQty(int pizzaQty) {
        this.pizzaQty = pizzaQty;
    }

    public int getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(int pizzaSize) {
        this.pizzaSize = pizzaSize;
    }
}
