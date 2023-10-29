package com.example.project.adminActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.adapter.WalletAdminAdapter;
import com.example.project.api.APIClient;
import com.example.project.model.Wallet;
import com.example.project.service.WalletApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMainActivity extends AppCompatActivity {

    WalletApiService apiService = APIClient.getClient().create(WalletApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        initRecycleView();
        bottom_navigation();

    }
    public void initRecycleView(){

        RecyclerView recyclerView = findViewById(R.id.admin_all_wallet_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Create an instance of the WalletAdapter
        WalletAdminAdapter adapter = new WalletAdminAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        Call<ArrayList<Wallet>> call = apiService.getWallets();
        call.enqueue(new Callback<ArrayList<Wallet>>() {
            @Override
            public void onResponse(Call<ArrayList<Wallet>> call, Response<ArrayList<Wallet>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Wallet> wallets = response.body();
                    Log.d("responseSuccess", "This is a debug message");
                    Log.d("respondBody", response.body().toString());
                    adapter.setWalletList(wallets);
                } else {
                    Toast.makeText(AdminMainActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT);

                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Wallet>> call, Throwable t) {
                Toast.makeText(AdminMainActivity.this, "Load wallet list failed!", Toast.LENGTH_SHORT);
                // Handle network or request failure
            }
        });
    }

    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.switchToHomePageBtn);
        LinearLayout addBtn = findViewById(R.id.switchToAddPageBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(AdminMainActivity.this, AdminMainActivity.class)));
        addBtn.setOnClickListener(v -> startActivity(new Intent(AdminMainActivity.this, NewWalletActivity.class)));
    }



}