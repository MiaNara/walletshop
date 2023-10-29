package com.example.project.service;


import com.example.project.model.Wallet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WalletApiService {
    @GET("Wallets")
    Call<ArrayList<Wallet>> getWallets();
    @POST("Wallets")
    Call<Wallet> createWallet(@Body Wallet wallet);

    @PUT("Wallets" + "/{id}")
    Call<Wallet> updateWallet(@Path("id") Object id, @Body Wallet wallet);

    @DELETE("Wallets" + "/{id}")
    Call<Wallet> deleteWallet(@Path("id") Object id);
}
