package com.example.project.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Order;

import java.util.ArrayList;


public class EachOrderAdapter extends RecyclerView.Adapter<EachOrderAdapter.EachOrderViewHolder>{
    private ArrayList<Order> items;

    public EachOrderAdapter() {
        this.items = new ArrayList<>();
    }

    public ArrayList<Order> getItems() {
        return items;
    }

    public void setItems(ArrayList<Order> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public EachOrderAdapter.EachOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_order_item, parent, false);
        return new EachOrderAdapter.EachOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EachOrderAdapter.EachOrderViewHolder holder, int position) {
        Order item = items.get(position);
        Log.d("item trong each oder ne", item.toString());
        OrderAdapter adapter = new OrderAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.walletRv.setLayoutManager(layoutManager);
        holder.walletRv.setAdapter(adapter);
        adapter.setItems(item.getOrder());
        holder.tvLocation.setText(item.getLocation());
       holder.totalTxt.setText(item.getTotalPrice() + "đ");
       holder.tvPhone.setText(item.getPhone());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class EachOrderViewHolder extends RecyclerView.ViewHolder {
        RecyclerView walletRv;
        TextView tvLocation;
        TextView totalTxt;
        TextView tvPhone;

        public EachOrderViewHolder(View itemView) {
            super(itemView);
            walletRv = itemView.findViewById(R.id.wallet_rv);
            tvLocation = itemView.findViewById(R.id.deliveryTxt);
            totalTxt = itemView.findViewById(R.id.totalTxt);
            tvPhone = itemView.findViewById(R.id.tvPhone);

        }
    }
}
