package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.project.controller.CartManager;
import com.example.project.model.Cart;
import com.example.project.model.Wallet;

public class WalletDetailActivity extends AppCompatActivity {

    private TextView addToCartBtn;
    private TextView walletName, walletPrice, walletDescription, walletMaterial, walletSize;
    private ImageView walletImage, backBtn;

    private Wallet object;
    private int numberOrder = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        initView();
        getBundle();

    }
    private void initView(){
        backBtn = findViewById(R.id.backBtn);
        addToCartBtn = findViewById(R.id.addToCart);
        walletPrice = findViewById(R.id.walletPrice);
        walletName = findViewById(R.id.walletName);
        walletDescription = findViewById(R.id.walletDescription);
        walletImage = findViewById(R.id.walletImg);
        walletMaterial = findViewById(R.id.walletMaterial);
        walletSize = findViewById(R.id.walletSize);

    }
    private void getBundle(){
        Wallet object = getIntent().getParcelableExtra("object");

        Glide.with(this)
                .load(object.getImage())
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional transition effect
                .into(walletImage);
        walletName.setText(object.getName());
        walletPrice.setText(object.getPrice());
        walletDescription.setText(object.getDescription());
        walletMaterial.setText(object.getMaterial());
        walletSize.setText(object.getSize());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletDetailActivity.this, MainActivity.class));
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the wallet to the car
                CartManager storage = new CartManager();
                storage.addToStorage(object);
                // Provide feedback to the user (e.g., show a toast)
                Toast.makeText(WalletDetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WalletDetailActivity.this, MainActivity.class));
            }
        });



    }
//    private void bottom_navigation() {
//        LinearLayout homeBtn = findViewById(R.id.homeBtn);
//        LinearLayout cartBtn = findViewById(R.id.cartBtn);
//        homeBtn.setOnClickListener(v -> startActivity(new Intent(WalletDetailActivity.this, MainActivity.class)));
//        cartBtn.setOnClickListener(v -> startActivity(new Intent(WalletDetailActivity.this, CartActivity.class)));
//    }
}