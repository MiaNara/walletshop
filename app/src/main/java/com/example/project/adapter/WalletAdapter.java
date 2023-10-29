package com.example.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.project.R;
import com.example.project.WalletDetailActivity;
import com.example.project.model.Wallet;
import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private ArrayList<Wallet> walletList;
    private Context context;
    public void setWalletList(ArrayList<Wallet> walletList) {
        this.walletList = walletList;
        notifyDataSetChanged();
    }
    public WalletAdapter(ArrayList<Wallet> walletList, Context context) {
        this.walletList = walletList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder holder, int position) {
        Wallet wallet = walletList.get(position);
        holder.walletName.setText(wallet.getName());
        holder.walletPrice.setText(wallet.getPrice() + "đ");
        String imageUrl = wallet.getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional transition effect
                .into(holder.walletImage);

        holder.walletImage.setOnClickListener(v -> {
            // Start the WalletDetailActivity and pass the wallet object
            Intent intent = new Intent(context, WalletDetailActivity.class);
            intent.putExtra("object", wallet);
            context.startActivity(intent);
        });
        holder.walletName.setOnClickListener(v -> {
            // Start the WalletDetailActivity and pass the wallet object
            Intent intent = new Intent(context, WalletDetailActivity.class);
            intent.putExtra("object", wallet);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView walletImage;
        TextView walletName;
        TextView walletPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            walletImage = itemView.findViewById(R.id.walletImg);
            walletName = itemView.findViewById(R.id.walletName);
            walletPrice = itemView.findViewById(R.id.walletPrice);
        }
    }

}
