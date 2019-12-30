package com.example.modabba;

public class OrderSatusModel {
    String description;
    String date;
    String orderStatus;

    OrderSatusModel() { }

    public OrderSatusModel(String description, String date, String orderStatus) {
        this.description = description;
        this.date = date;
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
