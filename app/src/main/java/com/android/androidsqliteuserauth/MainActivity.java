package com.android.androidsqliteuserauth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Button Loginbtn = findViewById(R.id.loginbtn);
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextNumberPassword);
        Loginbtn.setOnClickListener(v-> {
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            if(userEmail.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter All Details", Toast.LENGTH_LONG).show();
            }else{
                boolean exist = dbHelper.checkUser(userEmail, userPassword);
                if(!exist){
                    Toast.makeText(MainActivity.this, "Wrong Username Or Password!", Toast.LENGTH_LONG).show();

                }else{
                    Intent dashboardIntent = new Intent(MainActivity.this, Dashboard.class);
                    dashboardIntent.putExtra("email", userEmail);
                    startActivity(dashboardIntent);
                }
            }

        });
        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
            finish();
        });

    }
}