package com.example.project.controller;

import com.example.project.model.CartItem;
import com.example.project.model.Wallet;

import java.util.ArrayList;

public class CartManager {

    public static ArrayList<CartItem> storage = new ArrayList<>();
    public static int totalCart = 0;
    public void setStorage(ArrayList<CartItem> storage) {
        CartManager.storage = storage;
    }

    public void addToStorage(Wallet object) {
        boolean isExisted = false;
        int index = 0;
       for(int i = 0; i < this.getStorage().size(); i++){
           if(this.getStorage().get(i).getWallet().getId().equals(object.getId())){
               isExisted = true;
               index = i;
               break;
           }
       }
       if(isExisted){
           this.getStorage().get(index).setAmount( this.getStorage().get(index).getAmount() + 1);
       } else {
        storage.add(new CartItem(object, 1));
    }


    }
    public void increaseNumberOfItems(Wallet object){
        for(CartItem item : storage){
            if(item.getWallet().getId() == object.getId()){
                item.setAmount(item.getAmount() + 1);
                break;
            }
        }
    }

    public void decreaseNumberOfItems(Wallet object){
        for(CartItem item : storage){
            if(item.getWallet().getId() == object.getId()){
                item.setAmount(item.getAmount() - 1);
                if (item.getAmount() == 0){
                    storage.remove(item);
                }
                break;
            }
        }

    }

//    public int totalCart(ArrayList<CartItem> cartItem) {
//        int total = 0;
//
//        for (CartItem item : cartItem) {
//            total += Math.multiplyExact(item.getAmount(), Integer.parseInt(item.getWallet().getPrice()));
//        }
//        return total;
//    }
    public ArrayList<CartItem> getStorage() {
        return storage;
    }


}
