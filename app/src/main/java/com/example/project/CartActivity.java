package com.example.project;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.adapter.CartAdapter;

import com.example.project.api.APIClient;
import com.example.project.controller.CartManager;
import com.example.project.model.CartItem;
import com.example.project.model.Order;
import com.example.project.service.OrderApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    TextView totalTxt, emptyTxt;
    ImageView backBtn, backBtn2;
    Button checkoutButton;
    ScrollView scrollView;
    CartManager storage = new CartManager();
    EditText location, phone;
    String address;
    OrderApiService apiService = APIClient.getClient().create(OrderApiService.class);
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        getBundle();

    }

    private void init() {
        recyclerView = findViewById(R.id.cart_rv);
        totalTxt = findViewById(R.id.totalTxt);
        backBtn = findViewById(R.id.backBtn);
        backBtn2 = findViewById(R.id.backBtn2);
        checkoutButton = findViewById(R.id.checkoutBtn);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView2);
        location = findViewById(R.id.location);
        phone = findViewById(R.id.phone);


    }

    private void getBundle() {
        if (storage.getStorage().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            backBtn2.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            backBtn2.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, MainActivity.class)));

        } else {
            emptyTxt.setVisibility(View.GONE);
            backBtn2.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            cartAdapter = new CartAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(cartAdapter);
            cartAdapter.setItems(storage.getStorage());
            int totalPrice = totalCart();
            totalTxt.setText(String.valueOf(totalPrice));
            backBtn.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, MainActivity.class)));
            String email = getIntent().getStringExtra("email");
            checkoutButton.setOnClickListener(v -> {
                Order newOrder = new Order(storage.getStorage(),totalPrice, email, location.getText().toString(), phone.getText().toString() );
                createOrder(newOrder);

            });
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Location permission is granted, proceed with location sharing
            shareLocation();
        }

    }

    public void updateTotalText() {
        int total = totalCart();
        TextView totalTxt = findViewById(R.id.totalTxt);
        totalTxt.setText(String.valueOf(total));
    }

    ;

    public int totalCart() {
        CartManager storage = new CartManager();
        int total = 0;
        for (CartItem item : storage.getStorage()) {
            total += Math.multiplyExact(item.getAmount(), Integer.parseInt(item.getWallet().getPrice()));
        }
        return total;
    }
    public void createOrder(Order order) {
        apiService.createOrder(order)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                        storage.setStorage(new ArrayList<>());
                        String email = getIntent().getStringExtra("email");
                        Toast.makeText(CartActivity.this, "Order successfully!", Toast.LENGTH_SHORT).show();
                        Intent newIntent = new Intent(CartActivity.this, OrderActivity.class);
                        newIntent.putExtra("email", email);
                        startActivity(newIntent);

                    }

                    @Override
                    public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                        Toast.makeText(CartActivity.this, "An error has occurred!", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void shareLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Use these latitude and longitude values in the delivery address.
                    updateDeliveryAddress(latitude, longitude);
                    locationManager.removeUpdates(this); // Stop listening for updates
                }
                // Implement other LocationListener methods as needed
            };
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            // Handle the case where GPS is not enabled
        }
    }
    private void updateDeliveryAddress(double latitude, double longitude) {
        EditText deliveryAddressText = findViewById(R.id.location);
       address = getCompleteAddressString(latitude, longitude);
        deliveryAddressText.setText(address);
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                }
                strAdd = strReturnedAddress.toString();
            } else {
                Log.w("My Current location address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current location address", "Canont get Address!");
        }
        return strAdd;
    }
}
