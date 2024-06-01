package com.example.couponmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAds extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdAdapter adAdapter;
    private DBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        Intent intent = getIntent();

        recyclerView = findViewById(R.id.recyclerViewMyAds);
        databaseHelper = new DBHelper(this, "my_database.db");
        adAdapter = new AdAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adAdapter);

        // Fetch ads from the database using Cursor
        Cursor cursor = databaseHelper.getAllAdsCursor();

        // Pass the Cursor to the adapter
        adAdapter.setCursor(cursor);
    }
}