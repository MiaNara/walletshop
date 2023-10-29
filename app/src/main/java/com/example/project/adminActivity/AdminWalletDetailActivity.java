package com.example.project.adminActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.project.R;
import com.example.project.model.Wallet;

public class AdminWalletDetailActivity extends AppCompatActivity {
    private TextView walletName, walletPrice, walletDescription, walletMaterial, walletSize;
    private ImageView walletImage, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_wallet_detail);
        initView();
        getBundle();
        bottom_navigation();

    }

    private void initView(){
        backBtn = findViewById(R.id.backBtn);
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

        backBtn.setOnClickListener(v -> startActivity(new Intent(AdminWalletDetailActivity.this, AdminMainActivity.class)));

    }
    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.switchToHomePageBtn);
        LinearLayout addBtn = findViewById(R.id.switchToAddPageBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(AdminWalletDetailActivity.this, AdminMainActivity.class)));
        addBtn.setOnClickListener(v -> startActivity(new Intent(AdminWalletDetailActivity.this, NewWalletActivity.class)));
    }
}
