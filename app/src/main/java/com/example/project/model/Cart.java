package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Wallet> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Wallet wallet) {
        items.add(wallet);
    }

    public void removeItem(Wallet wallet) {
        items.remove(wallet);
    }

    public List<Wallet> getItems() {
        return items;
    }

    public double calculateTotalPrice() {
        double total = 0.0;
        for (Wallet wallet : items) {
            total += Double.parseDouble(wallet.getPrice());
        }
        return total;
    }
}
