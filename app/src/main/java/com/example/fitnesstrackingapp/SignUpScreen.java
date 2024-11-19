package com.example.fitnesstrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signupscreen);

        EditText edtName, edtUsername, edtPassword;
        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        Button signUpBtn = findViewById(R.id.btnSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpScreen.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper dbhandler = new DatabaseHelper(SignUpScreen.this);
                    if(dbhandler.registerUser(name,username,password)){
                        Intent goDashboardScreen = new Intent(SignUpScreen.this, UserDashboard.class);
                        startActivity(goDashboardScreen);
                    }
                    else{
                        Toast.makeText(SignUpScreen.this, "Account creation failed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginScreen = new Intent(SignUpScreen.this, MainActivity.class);
                startActivity(goLoginScreen);
            }
        });
    }
}