package com.example.project.adminActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;

import com.example.project.controller.WalletController;
import com.example.project.model.Wallet;


public class EditWalletActivity extends AppCompatActivity {
//    WalletApiService apiService = APIClient.getClient().create(WalletApiService.class);
    WalletController controller = new WalletController();
    EditText etName, etPrice, etDescription, etMaterial, etSize, etImage;
    TextView saveBtn;
    ImageView backBtn;
    Wallet object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wallet);
        init();
        getBundle();
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

    }
    private void getBundle(){
        object = getIntent().getParcelableExtra("object");
        Log.d("object la gi", object.getId().toString());
        etImage.setText(object.getImage());
        etName.setText(object.getName());
        etPrice.setText(object.getPrice());
        etDescription.setText(object.getDescription());
        etMaterial.setText(object.getMaterial());
        etSize.setText(object.getSize());

        backBtn.setOnClickListener(v -> startActivity(new Intent(EditWalletActivity.this, AdminMainActivity.class)));
        saveBtn.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String price = etPrice.getText().toString();
            String description = etDescription.getText().toString();
            String material = etMaterial.getText().toString();
            String size = etSize.getText().toString();
            String image = etImage.getText().toString();
            if (!name.isEmpty() && !price.isEmpty() && !description.isEmpty() && !material.isEmpty() && !size.isEmpty() && !image.isEmpty() ) {
                Wallet wallet = new Wallet(name, price, image, description, material,size);
                controller.updateWallet(object, wallet, EditWalletActivity.this);
            }
            else {
                Toast.makeText(EditWalletActivity.this, "Please enter all the field!", Toast.LENGTH_SHORT).show();

            }


        });

    }
//    private void bottom_navigation() {
//        LinearLayout homeBtn = findViewById(R.id.switchToHomePageBtn);
//        LinearLayout addBtn = findViewById(R.id.switchToAddPageBtn);
//        homeBtn.setOnClickListener(v -> startActivity(new Intent(EditWalletActivity.this, AdminMainActivity.class)));
//        addBtn.setOnClickListener(v -> startActivity(new Intent(EditWalletActivity.this, NewWalletActivity.class)));
//    }

}