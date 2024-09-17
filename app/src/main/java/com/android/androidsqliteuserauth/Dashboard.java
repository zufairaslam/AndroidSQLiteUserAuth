package com.android.androidsqliteuserauth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.util.HashMap;

public class Dashboard extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dashboard_activity);
        TextView welV = findViewById(R.id.welcomeView);
        TextView idV = findViewById(R.id.idView);
        TextView nameV = findViewById(R.id.nameView);
        TextView emailV = findViewById(R.id.emailView);
        TextView passV = findViewById(R.id.passView);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        HashMap<String, String> user = dbHelper.getUser(email);
        String id = user.get("id");
        String name = user.get("name");
        String mail = user.get("email");
        String password = user.get("password");

        welV.setText("Welcome");
        idV.setText(id);
        nameV.setText(name);
        emailV.setText(mail);
        passV.setText(password);

    }
}
