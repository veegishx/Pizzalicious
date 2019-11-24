package Model;

import java.io.Serializable;

public class Pizza implements Serializable {
    private String pizzaType;
    private int pizzaPrice;
    private int pizzaQty;
    private String pizzaSize;

    public Pizza() {
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "pizzaType='" + pizzaType + '\'' +
                ", pizzaPrice=" + pizzaPrice +
                ", pizzaQty=" + pizzaQty +
                ", pizzaSize='" + pizzaSize + '\'' +
                '}';
    }

    public Pizza(String pizzaType, int pizzaPrice, int pizzaQty, String pizzaSize) {
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

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }
}
