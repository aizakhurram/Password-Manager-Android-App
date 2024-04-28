package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserRegistered extends AppCompatActivity {

    RecyclerView rvUsers;
    LinearLayoutManager manager;
    UserAdapter adapter;
    FloatingActionButton fabDelete, fabAdd;
    ArrayList<User> user;
    ImageView ivEdit;
    Button etName,etPassword;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registered);
        init();
        Intent intent=getIntent();
        userid=intent.getIntExtra("userid",-1);

    }
    private void init()
    {

        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvUsers.setLayoutManager(manager);
        fabDelete =findViewById(R.id.fabDelete);
        fabAdd =findViewById(R.id.fabAdd);

        Intent intent=getIntent();
        userid=intent.getIntExtra("userid",-1);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegistered.this, RecycleBin.class));
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegistered.this, addName.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
                finish();
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.open();
        user = databaseHelper.readAllUrls(userid);
        databaseHelper.close();

        adapter = new UserAdapter(this, user);
        rvUsers.setAdapter(adapter);

    }

}