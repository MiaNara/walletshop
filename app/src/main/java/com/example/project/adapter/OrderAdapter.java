package com.example.project.adapter;

import android.util.Log;
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
import com.example.project.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<CartItem> items;


    public OrderAdapter() {
        this.items = new ArrayList<>();
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.itemNameTextView.setText(item.getWallet().getName());
        Log.d("item name trong order ne" ,item.getWallet().getName());
        String imageUrl = item.getWallet().getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional transition effect
                .into(holder.itemImageView);
        holder.itemPriceTextView.setText(item.getWallet().getPrice() + "đ");
        holder.itemQuantityTextView.setText(item.getAmount().toString());
        String totalMoney = String.valueOf(Math.multiplyExact(item.getAmount(), Integer.parseInt(item.getWallet().getPrice())));
        holder.totalItemMoney.setText(totalMoney + "đ");
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemQuantityTextView, totalItemMoney;

        ImageView itemImageView;
        public OrderViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
            totalItemMoney = itemView.findViewById(R.id.totalItemMoney);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            // You can find and initialize other views here
        }
    }
}
