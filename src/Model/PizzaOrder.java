package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class PizzaOrder implements Serializable {
    private int orderId;
    private double orderTotalPrice;
    private int orderedBy;
    private int staffID;
    private ArrayList<Pizza> orderContent;
    private String pizza;
    private int status;



    public  PizzaOrder(){
    }
    public PizzaOrder(int orderId, double orderTotalPrice, int orderedBy, int staffID, ArrayList<Pizza> orderContent) {
        this.orderId = orderId;
        this.orderTotalPrice = orderTotalPrice;
        this.orderedBy = orderedBy;
        this.staffID = staffID;
        this.orderContent = orderContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PizzaOrder(String pizza, int orderId, double orderTotalPrice, int orderedBy, int status) {
        this.orderId = orderId;
        this.orderTotalPrice = orderTotalPrice;
        this.orderedBy = orderedBy;
        this.pizza = pizza;
        this.status = status;
    }

    public String getPizza() {
        return pizza;
    }

    public void setPizza(String pizza) {
        this.pizza = pizza;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public int getOrderedBy() {
        return orderedBy;
    }

    @Override
    public String toString() {
        return "PizzaOrder{" +
                "orderId=" + orderId +
                ", orderTotalPrice=" + orderTotalPrice +
                ", orderedBy=" + orderedBy +
                ", staffID=" + staffID +
                ", pizza=" + pizza +
                '}';
    }

    public void setOrderedBy(int orderedBy) {
        this.orderedBy = orderedBy;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public ArrayList<Pizza> getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(ArrayList<Pizza> orderContent) {
        this.orderContent = orderContent;
    }

}
