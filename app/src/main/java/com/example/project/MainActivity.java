package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.adapter.WalletAdapter;
import com.example.project.api.APIClient;
import com.example.project.controller.CartManager;
import com.example.project.model.Wallet;
import com.example.project.service.WalletApiService;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    CartManager cart = new CartManager();
    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";
    WalletApiService apiService = APIClient.getClient().create(WalletApiService.class);
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String email;

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!cart.getStorage().isEmpty()){
            displayNotification(cart.getStorage().size());
            Snackbar.make(findViewById(android.R.id.content), "Bạn có " + cart.getStorage().size()+ " món hàng đang chờ thanh toán", Snackbar.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (currentUser != null) {
            email = currentUser.getEmail();
            // Now you can use userEmail throughout your app
        }
        initRecycleView();
        bottom_navigation();

    }

    public void initRecycleView() {
        TextView userEmail = findViewById(R.id.userEmail);
        userEmail.setText(email);
        RecyclerView recyclerView = findViewById(R.id.all_wallet_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        WalletAdapter adapter = new WalletAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        Call<ArrayList<Wallet>> call = apiService.getWallets();
        call.enqueue(new Callback<ArrayList<Wallet>>() {
            @Override
            public void onResponse(Call<ArrayList<Wallet>> call, Response<ArrayList<Wallet>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Wallet> wallets = response.body();
                    assert response.body() != null;
                    adapter.setWalletList(wallets);
                } else {
                    Toast.makeText(MainActivity.this, "Can not load wallet list data", Toast.LENGTH_SHORT).show();
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
        LinearLayout orderBtn = findViewById(R.id.orderBtn);
        LinearLayout personalBtn = findViewById(R.id.profileBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity.class)));
        cartBtn.setOnClickListener(v -> {
//            String email = getIntent().getStringExtra("email");
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
        orderBtn.setOnClickListener(v -> {
//            String email = getIntent().getStringExtra("email");
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
        personalBtn.setOnClickListener(v -> {
//            String email = getIntent().getStringExtra("email");
            Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESC;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void displayNotification(int numberOfItemsInCart) {
        int permissionState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESC;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_notifications_active_24)
                    .setContentTitle("MotaWallet")
                    .setContentText("Bạn có "+ numberOfItemsInCart + " món hàng đang chờ thanh toán!")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Bạn có "+ numberOfItemsInCart + " món hàng đang chờ thanh toán!"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);


            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManagerCompat.notify(1, builder.build());
        }

    }

}