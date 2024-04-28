package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecycleBin extends AppCompatActivity {


    RecyclerView rvDeletedEntries;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycle_bin);


        // Initialize RecyclerView and set layout manager
        rvDeletedEntries = findViewById(R.id.rvDeletedEntries);
        rvDeletedEntries.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve deleted users from the UserAdapter
        ArrayList<User> deletedUsers = UserAdapter.deletedUsers;

        // Set up adapter for RecyclerView with the retrieved deleted users
        adapter = new UserAdapter(this, deletedUsers);
        rvDeletedEntries.setAdapter(adapter);
    }
}