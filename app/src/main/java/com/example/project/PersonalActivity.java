package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.adminActivity.AdminMainActivity;
import com.example.project.adminActivity.AdminOrderActivity;
import com.example.project.adminActivity.NewWalletActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class PersonalActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        TextView logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(PersonalActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(PersonalActivity.this, "Log out successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
        bottom_navigation();


    }

    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout orderBtn = findViewById(R.id.orderBtn);
        LinearLayout personalBtn = findViewById(R.id.profileBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(PersonalActivity.this, MainActivity.class)));
        cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalActivity.this, CartActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
        orderBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalActivity.this, OrderActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
        personalBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalActivity.this, PersonalActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
    }


}