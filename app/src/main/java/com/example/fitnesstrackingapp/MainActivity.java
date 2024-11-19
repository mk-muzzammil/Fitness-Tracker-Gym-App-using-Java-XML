package com.example.fitnesstrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText signupUsername = findViewById(R.id.loginUsername);
        EditText signupPassword = findViewById(R.id.loginPassword);

        Button signupBtn = findViewById(R.id.btnLogin);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                DatabaseHelper dbhandler = new DatabaseHelper(MainActivity.this);
                if(dbhandler.checkUser(username, password)){
                    Toast.makeText(MainActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
                    Intent goDashboardScreen = new Intent(MainActivity.this, UserDashboard.class);
                    startActivity(goDashboardScreen);
                }
                else{
                    Toast.makeText(MainActivity.this,"Login failed", Toast.LENGTH_SHORT).show();

                }

            }
        });
}}