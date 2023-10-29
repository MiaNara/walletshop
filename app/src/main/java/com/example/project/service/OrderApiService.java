package com.example.project.service;

import com.example.project.model.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApiService {
    @GET("Orders")
    Call<ArrayList<Order>> getOrders();
    @POST("Orders")
    Call<Order> createOrder(@Body Order wallet);


}
