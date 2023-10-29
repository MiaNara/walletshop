package com.example.project;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.helper.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView loginBtn, resetPasswordBtn, registerBtn;
    private EditText  etEmail, etPassword, etRePassword;
    private Validation validation = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        init();
    }
    public void init(){
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        resetPasswordBtn = findViewById(R.id.resetPasswordBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            goToLogin();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register() {
        etEmail = findViewById(R.id.email);
         etPassword = findViewById(R.id.password);
         etRePassword = findViewById(R.id.rePassword);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();

        if(email.length() == 0 || password.length() == 0 || rePassword.length() == 0 ){
            Toast.makeText(this, "Please enter email and password!", Toast.LENGTH_SHORT).show();
        } else  if(!validation.validateEmail(email)){
            Toast.makeText(this, "Email is not correct pattern!", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(rePassword)){
            Toast.makeText(this, "Password and re-enter password are not match!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Registration successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToLogin();
                            Toast.makeText(this, "Registration successful: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            // Registration failed
                            Toast.makeText(this, "Registration failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
