package com.android.androidsqliteuserauth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class Register extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Button loginBtn = findViewById(R.id.loginbtn);
        loginBtn.setOnClickListener(v-> {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        Button registerBtn = findViewById(R.id.registerBtn);
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText name = findViewById(R.id.editTextText);
        EditText password = findViewById(R.id.editTextNumberPassword);

        registerBtn.setOnClickListener(v-> {
            String userEmail = email.getText().toString();
            String userName = name.getText().toString();
            String userPassword = password.getText().toString();
            if(userEmail.isEmpty() || userName.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(Register.this, "Please Enter All Details", Toast.LENGTH_LONG).show();
            }else{
                boolean exist = dbHelper.checkUserExist(userEmail);
                if(!exist){
                    boolean isInserted = dbHelper.insertUser(userName, userEmail, userPassword, new Date().toString());
                    if (isInserted) {
                        Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "User Registered failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "User Already Exist", Toast.LENGTH_SHORT).show();

                }

            }


        });

    }
}
