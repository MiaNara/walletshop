package com.example.project.model;

import java.util.ArrayList;

public class Order {
    private ArrayList<CartItem> order;
    private int totalPrice;
    private String email;
    private String location;

//    public Order(ArrayList<CartItem> order, String email, String location) {
//            this.order = order;
//        this.email = email;
//        this.location = location;
//    }

    public Order(ArrayList<CartItem> order, int totalPrice, String email, String location) {
        this.order = order;
        this.totalPrice = totalPrice;
        this.email = email;
        this.location = location;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<CartItem> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<CartItem> order) {
        this.order = order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
