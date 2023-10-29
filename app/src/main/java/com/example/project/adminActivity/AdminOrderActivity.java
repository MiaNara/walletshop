package com.example.project.adminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.adapter.EachOrderAdapter;
import com.example.project.api.APIClient;
import com.example.project.model.Order;
import com.example.project.service.OrderApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class AdminOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EachOrderAdapter adapter;
    TextView emptyTxt;
    ImageView backBtn3, backBtn4;
    ScrollView scrollView;

    OrderApiService apiService = APIClient.getClient().create(OrderApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        getBundle();

    }
    private void init() {
        recyclerView = findViewById(R.id.eachOrderRv);
        backBtn3 = findViewById(R.id.backBtn3);
        backBtn4 = findViewById(R.id.backBtn4);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.orderScrollView);
    }
    private void getBundle() {
        Call<ArrayList<Order>> call = apiService.getOrders();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Order> orders = response.body();

                    if (orders == null || orders.isEmpty()) {
                        emptyTxt.setVisibility(View.VISIBLE);
                        backBtn3.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                        backBtn3.setOnClickListener(v -> startActivity(new Intent(AdminOrderActivity.this, AdminMainActivity.class)));

                    } else {
                        emptyTxt.setVisibility(View.GONE);
                        backBtn3.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        backBtn4.setOnClickListener(v -> startActivity(new Intent(AdminOrderActivity.this, AdminMainActivity.class)));
                        adapter = new EachOrderAdapter();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(AdminOrderActivity.this);
                        recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setAdapter(adapter);
                        adapter.setItems(orders);
                    }

                } else {
                    Toast.makeText(AdminOrderActivity.this, "Can not load order list data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(AdminOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}