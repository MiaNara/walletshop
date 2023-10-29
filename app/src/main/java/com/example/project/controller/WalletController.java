package com.example.project.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.example.project.adminActivity.AdminMainActivity;
import com.example.project.api.APIClient;
import com.example.project.model.Wallet;
import com.example.project.service.WalletApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletController {
    WalletApiService apiService = APIClient.getClient().create(WalletApiService.class);



    public void updateWallet(Wallet currentWallet, Wallet wallet, Context context) {
        apiService.updateWallet(currentWallet.getId(), wallet)
                .enqueue(new Callback<Wallet>() {
                    @Override
                    public void onResponse(@NonNull Call<Wallet> call, @NonNull Response<Wallet> response) {
                        Toast.makeText(context, "Wallet " + wallet.getName() + " has been updated!", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, AdminMainActivity.class));

                    }

                    @Override
                    public void onFailure(@NonNull Call<Wallet> call, @NonNull Throwable t) {
                        Toast.makeText(context, "An error has occurred!", Toast.LENGTH_SHORT).show();

                    }

                });
    }
    public void deleteWallet(Wallet wallet,Context context){
        apiService.deleteWallet(wallet.getId())
                .enqueue(new Callback<Wallet>() {
                    @Override
                    public void onResponse(@NonNull Call<Wallet> call, @NonNull Response<Wallet> response) {
                        context.startActivity(new Intent(context, AdminMainActivity.class));
                        Toast.makeText(context, "Wallet " + wallet.getName() + " has been deleted!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Wallet> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Delete failed!", Toast.LENGTH_SHORT).show();
                    }

                });
    }
    public void showConfirmationDialog(Wallet wallet, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation"); // Set the dialog title
        builder.setMessage("Do you want delete " + wallet.getName() + " ?"); // Set the dialog message

        builder.setPositiveButton("Yes", (dialog, which) -> {
            deleteWallet(wallet, context);
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
