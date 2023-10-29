package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.project.CartActivity;
import com.example.project.R;
import com.example.project.controller.CartManager;
import com.example.project.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> items;
    CartManager storage = new CartManager();

    public CartAdapter() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.itemNameTextView.setText(item.getWallet().getName());
        String imageUrl = item.getWallet().getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional transition effect
                .into(holder.itemImageView);
        holder.itemPriceTextView.setText(item.getWallet().getPrice() + "đ");
        holder.itemQuantityTextView.setText(item.getAmount().toString());
        String totalMoney = String.valueOf(Math.multiplyExact(item.getAmount(), Integer.parseInt(item.getWallet().getPrice())));
        holder.totalItemMoney.setText(totalMoney + "đ");
        holder.increaseImageView.setOnClickListener(v -> {
            storage.increaseNumberOfItems(item.getWallet());
            ((CartActivity) holder.itemView.getContext()).updateTotalText();

            notifyDataSetChanged();


        });
        holder.decreaseImageView.setOnClickListener(v -> {
            storage.decreaseNumberOfItems(item.getWallet());
            ((CartActivity) holder.itemView.getContext()).updateTotalText();
            notifyDataSetChanged();

        });

        // You can set other properties as well
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemQuantityTextView, totalItemMoney;

        ImageView itemImageView;
        ImageView increaseImageView;
        ImageView decreaseImageView;
        public CartViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
            increaseImageView = itemView.findViewById(R.id.increaseImageView);
            decreaseImageView = itemView.findViewById(R.id.decreaseImageView);
            totalItemMoney = itemView.findViewById(R.id.totalItemMoney);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            // You can find and initialize other views here
        }
    }
}
