package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project.adminActivity.AdminMainActivity;
import com.example.project.helper.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final Validation validation = new Validation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        init();
    }
    public void init(){
        TextView loginBtn = findViewById(R.id.loginBtn);
        TextView registerBtn = findViewById(R.id.registerBtn);
        TextView resetPasswordBtn = findViewById(R.id.resetPasswordBtn);
        registerBtn.setOnClickListener(v -> goToRegister());
        loginBtn.setOnClickListener(v -> {
            login();
        });
    }

    public void login() {
        EditText etEmail = findViewById(R.id.email);
        EditText etPassword = findViewById(R.id.password);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if(email.length() != 0 &&! validation.validateEmail(email)){
            Toast.makeText(this, "Please input correct email!", Toast.LENGTH_SHORT).show();
        }
        if(password.length() != 0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Login successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(this, "Login successful: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            if (user.getEmail().equals("admin@gmail.com")){
                                Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                                intent.putExtra("email", user.getEmail());
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email", user.getEmail());
                                startActivity(intent);
                            }

                        } else {
                            // Login failed
                            Toast.makeText(this, "Email or password not correct!" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
