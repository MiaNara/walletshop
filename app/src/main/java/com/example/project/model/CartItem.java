package com.example.project.model;

public class CartItem {
    private Wallet wallet;
    private Integer amount;

    public CartItem(Wallet wallet, Integer amount) {
        this.wallet = wallet;
        this.amount = amount;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
