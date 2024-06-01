package com.example.couponmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private TextView textViewLocation;
    private RecyclerView recyclerView;
    private RecyclerView featuredItemsRecyclerView;
    private FeaturedItemsAdapter featuredItemsAdapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the TextView for location in the toolbar
        textViewLocation = toolbar.findViewById(R.id.textViewLocation);

        // Example: Set a dummy location for demonstration
        String locationn = "Indore";
        textViewLocation.setText(locationn);
        //<----------------------------------------------------->

        searchEditText = findViewById(R.id.searchEditText);

        // Add TextWatcher to EditText
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform search operation when user presses Enter key
                    performSearch(searchEditText.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        //<---------------------------------------------->
        //database work
        // Create an instance of DBHelper
        dbHelper = new DBHelper(this, "appdata.db");

        // Initialize RecyclerView
        featuredItemsRecyclerView = findViewById(R.id.featuredItemsRecyclerView);
        featuredItemsAdapter = new FeaturedItemsAdapter(null); // Initialize adapter with null cursor initially
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        featuredItemsRecyclerView.setLayoutManager(layoutManager);
        featuredItemsRecyclerView.setAdapter(featuredItemsAdapter);

        // Retrieve ads from the database and set them in the adapter
        Cursor cursor = dbHelper.getAllAdsCursor();
        if (cursor != null && cursor.moveToFirst()) {
            featuredItemsAdapter.setCursor(cursor);
        }

        //<------------------------------------------->
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        dbHelper.close();
    }

    private void performSearch(String query) {
        Log.d("SearchQuery", "Query: " + query);
        if (featuredItemsAdapter != null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] projection = {
                    "Title",
                    "Description",
                    "Price",
                    "Location",
                    "CategoryID",
            };
            // Define the selection and selection arguments based on the search query
            String selection = "title LIKE ? OR description LIKE ?";
            String[] selectionArgs = {"%" + query + "%", "%" + query + "%"};
            // Query the database
            Cursor cursor = db.query(
                    "Ad",
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            //Check Cursor Contents
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        Log.d("CursorData", cursor.getColumnName(i) + ": " + cursor.getString(i));
                    }
                } while (cursor.moveToNext());
            }
            if (cursor != null && cursor.moveToFirst()) {
                // Access data from the cursor
                // Update the adapter with the new Cursor
                featuredItemsAdapter.setCursor(cursor);
            } else {
                // Handle empty or null cursor
                // Update the adapter with the new Cursor
                featuredItemsAdapter.setCursor(cursor);
            }

        } else {
            // Handle case when cursor is null
            // For example, display a message to the user
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }
    }

    public void home(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void chat(View v) {
        Intent intent = new Intent(this, CHAT.class);
        startActivity(intent);
    }

    public void sell(View v) {
        Intent intent = new Intent(this, SELL.class);
        startActivity(intent);
    }

    public void ads(View v) {
        Intent intent = new Intent(this, MyAds.class);
        startActivity(intent);
    }

    public void profile(View v) {
        Intent intent = new Intent(this, PROFILE.class);
        startActivity(intent);
    }


}
