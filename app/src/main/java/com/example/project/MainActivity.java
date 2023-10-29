package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.adapter.WalletAdapter;
import com.example.project.api.APIClient;
import com.example.project.model.Wallet;
import com.example.project.service.WalletApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    WalletApiService apiService = APIClient.getClient().create(WalletApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycleView();
        bottom_navigation();

    }
    public void initRecycleView(){

        RecyclerView recyclerView = findViewById(R.id.all_wallet_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Create an instance of the WalletAdapter
        WalletAdapter adapter = new WalletAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        // Make a network request to fetch wallet data
        Call<ArrayList<Wallet>> call = apiService.getWallets();
        call.enqueue(new Callback<ArrayList<Wallet>>() {
            @Override
            public void onResponse(Call<ArrayList<Wallet>> call, Response<ArrayList<Wallet>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Wallet> wallets = response.body();
                    Log.d("responseSuccess", "This is a debug message");
                    assert response.body() != null;
                    Log.d("respondBody", response.body().toString());



                    // Update the adapter with the retrieved data
                    adapter.setWalletList(wallets);
                } else {
                    Toast.makeText(MainActivity.this,"Can not load wallet list data", Toast.LENGTH_SHORT).show();
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Wallet>> call, Throwable t) {
                // Handle network or request failure
            }
        });
    }

    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
                homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity.class)));
                cartBtn.setOnClickListener(v ->{
                    String email = getIntent().getStringExtra("email");
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    intent.putExtra("email", "trangle@gmail.com");
                    startActivity(intent);
                });
    }



}