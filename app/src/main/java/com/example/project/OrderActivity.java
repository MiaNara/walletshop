package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.adapter.EachOrderAdapter;
import com.example.project.api.APIClient;
import com.example.project.model.Order;
import com.example.project.service.OrderApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
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
        String email = getIntent().getStringExtra("email");
        Call<ArrayList<Order>> call = apiService.getOrders();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    String email = getIntent().getStringExtra("email");
                    ArrayList<Order> orders = response.body();
                    assert response.body() != null;
                    ArrayList<Order> order = new ArrayList<>() ;
                    assert orders != null;
                    for (int i = orders.size() - 1; i >= 0; i--) {
                        if (orders.get(i).getEmail().equals(email)) {
                            order.add(new Order(orders.get(i).getOrder(), orders.get(i).getTotalPrice(), orders.get(i).getEmail(), orders.get(i).getLocation(), orders.get(i).getPhone()));
                        }
                    }

                    if (order == null || order.isEmpty()) {
                        emptyTxt.setVisibility(View.VISIBLE);
                        backBtn3.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                        backBtn3.setOnClickListener(v -> startActivity(new Intent(OrderActivity.this, MainActivity.class)));

                    } else {
                        emptyTxt.setVisibility(View.GONE);
                        backBtn3.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        backBtn4.setOnClickListener(v -> startActivity(new Intent(OrderActivity.this, MainActivity.class)));
                        adapter = new EachOrderAdapter();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderActivity.this);
                        recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setAdapter(adapter);
                        adapter.setItems(order);
                    }

                } else {
                    Toast.makeText(OrderActivity.this, "Can not load order list data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void init() {
//        recyclerView = findViewById(R.id.order_rv);
//        totalTxt = findViewById(R.id.totalTxt);
//        backBtn3 = findViewById(R.id.backBtn3);
//        backBtn4 = findViewById(R.id.backBtn4);
//        emptyTxt = findViewById(R.id.emptyTxt);
//        scrollView = findViewById(R.id.orderScrollView);
//        tvLocation = findViewById(R.id.deliveryTxt);
//        deliveryText = findViewById(R.id.deliveryTxt);
//
//    }
//    private void getBundle() {
//        String email = getIntent().getStringExtra("email");
//        Call<ArrayList<Order>> call = apiService.getOrders();
//        call.enqueue(new Callback<ArrayList<Order>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
//                if (response.isSuccessful()) {
//                    ArrayList<Order> orders = response.body();
//                    assert response.body() != null;
//                    Order recentOrder = null;
//                    assert orders != null;
//                    for (int i = 0; i < orders.size(); i++) {
//                        if (orders.get(i).getEmail().equals(email)) {
//                            recentOrder = new Order(orders.get(i).getOrder(), orders.get(i).getTotalPrice(), orders.get(i).getEmail(), orders.get(i).getLocation());
//                            Log.d("recentOrder", recentOrder.getOrder().toString());
//                        }
//                    }
//
//                    if (recentOrder == null) {
//                        emptyTxt.setVisibility(View.VISIBLE);
//                        backBtn3.setVisibility(View.VISIBLE);
//                        scrollView.setVisibility(View.GONE);
//                        backBtn3.setOnClickListener(v -> startActivity(new Intent(OrderActivity.this, MainActivity.class)));
//
//                    } else {
//                        emptyTxt.setVisibility(View.GONE);
//                        backBtn3.setVisibility(View.GONE);
//                        scrollView.setVisibility(View.VISIBLE);
//                        backBtn4.setOnClickListener(v -> startActivity(new Intent(OrderActivity.this, MainActivity.class)));
//                        adapter = new OrderAdapter();
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderActivity.this);
//                        recyclerView.setLayoutManager(layoutManager);
//
//                        recyclerView.setAdapter(adapter);
//                        adapter.setItems(recentOrder.getOrder());
//                        deliveryText.setText(recentOrder.getLocation());
//                        totalTxt.setText(String.valueOf(recentOrder.getTotalPrice()) + "đ");
//                    }
//
//
//                } else {
//                    Toast.makeText(OrderActivity.this, "Can not load order list data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
//                Toast.makeText(OrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}