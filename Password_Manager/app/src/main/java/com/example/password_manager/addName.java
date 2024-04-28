package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addName extends AppCompatActivity {

    EditText etName, etPassword, etUrl;
    Button btnAdd, btnCancel;

    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_name);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etUrl = findViewById(R.id.etUrl);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        Intent intent=getIntent();
        userid=intent.getIntExtra("userid",-1);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addName.this, PasswordManager.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addUser() {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString();
        String url = etUrl.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(url)) {
            Toast.makeText(addName.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
        try {
            myDatabaseHelper.open();
            myDatabaseHelper.insertUrl(userid,name, password, url);

            Toast.makeText(addName.this, "User added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity after adding the user
        } catch (Exception e) {
            Toast.makeText(addName.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            myDatabaseHelper.close(); // Ensure database is closed regardless of exceptions
        }
    }
}