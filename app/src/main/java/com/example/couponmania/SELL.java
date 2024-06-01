package com.example.couponmania;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class SELL extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 101;
    private EditText editTextTitle, editTextDescription, editTextPrice, editTextLocation;
    private ImageView imageViewAd;
    private Button buttonChooseImage, buttonUpload;
    private Spinner spinnerCategory;
    private Uri selectedImageUri;
    private DBHelper dbHelper;

    // Declare category options
    private String[] categoryOptions = {"Electronics", "Clothing", "Books", "Furniture"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Intent intent = getIntent();
        checkCameraPermission();
        checkStoragePermission();

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPrice = findViewById(R.id.editTextPrice);
        imageViewAd = findViewById(R.id.imageViewAd);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        buttonUpload = findViewById(R.id.buttonUpload);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        editTextLocation = findViewById(R.id.editTextLocation);


        // Create ArrayAdapter for the spinner using the category options array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryOptions);

        // Set dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        spinnerCategory.setAdapter(adapter);

        dbHelper = new DBHelper(this,"my_database.db");

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();

            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAd();
                previouspage(v);
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                // Display the selected image in the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageViewAd.setImageBitmap(bitmap);
                Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error selecting image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadAd() {
        // Get data from UI components
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextPrice.getText().toString();
        String location = editTextLocation.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        // Check if an image is selected
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Description", description);
        values.put("Price", price);
        values.put("Location", location);
        values.put("CategoryID", category);
        values.put("ImageURLs", selectedImageUri.toString()); // Insert picture URI as string

        // Insert the data into the database
        db.insert("Ad", null, values);

        // Close the database
        db.close();

    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted
            } else {
                // Camera permission denied
            }
        } else if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            // Handle storage permission result
        }
    }
    public void previouspage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}