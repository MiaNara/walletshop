package com.example.project.adminActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.api.APIClient;
import com.example.project.model.Wallet;
import com.example.project.service.WalletApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewWalletActivity extends AppCompatActivity {

    WalletApiService apiService = APIClient.getClient().create(WalletApiService.class);
    EditText etName, etPrice, etDescription, etMaterial, etSize, etImage;
    TextView  saveBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wallet);
        init();
    }
    public void init(){
        etName = findViewById(R.id.editTextName);
        etPrice = findViewById(R.id.editTextPrice);
        etDescription = findViewById(R.id.editTextDescription);
        etMaterial = findViewById(R.id.editTextMaterial);
        etSize = findViewById(R.id.editTextSize);
        etImage = findViewById(R.id.editTextImage);
        saveBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> startActivity(new Intent(NewWalletActivity.this, AdminMainActivity.class)));
        saveBtn.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String price = etPrice.getText().toString();
            String description = etDescription.getText().toString();
            String material = etMaterial.getText().toString();
            String size = etSize.getText().toString();
            String image = etImage.getText().toString();
            if (!name.isEmpty() && !price.isEmpty() && !description.isEmpty() && !material.isEmpty() && !size.isEmpty() && !image.isEmpty() ) {
               Wallet wallet = new Wallet(name, price, image, description, material,size);
               createWallet(wallet);
            }
            else {
                Toast.makeText(NewWalletActivity.this, "Please enter all the field!", Toast.LENGTH_SHORT).show();

            }


        });
    }
    public void createWallet(Wallet wallet) {
        apiService.createWallet(wallet)
                .enqueue(new Callback<Wallet>() {
                    @Override
                    public void onResponse(@NonNull Call<Wallet> call, @NonNull Response<Wallet> response) {
                        Toast.makeText(NewWalletActivity.this, "Wallet " + wallet.getName() + " has been added!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewWalletActivity.this, AdminMainActivity.class));

                    }

                    @Override
                    public void onFailure(@NonNull Call<Wallet> call, @NonNull Throwable t) {
                        Toast.makeText(NewWalletActivity.this, "An error has occurred!", Toast.LENGTH_SHORT).show();

                    }

                });
    }

}