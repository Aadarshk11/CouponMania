package com.example.couponmania;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Profile_info extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone;
    private Button buttonSaveUser, buttonSelectPicture;
    private DBHelper dbHelper;
    private Uri selectedImageUri;
    private ImageView imageViewProfile;

    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        Intent intent = getIntent();

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonSaveUser = findViewById(R.id.buttonSaveUser);
        buttonSelectPicture = findViewById(R.id.buttonSelectPicture);
        imageViewProfile = findViewById(R.id.imageViewProfile);

        dbHelper = new DBHelper(this, "my_database.db");

        buttonSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });

        // Set click listener for the Save button
        buttonSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
                previous(v);
            }
        });


    }

    private void selectPicture() {
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
                imageViewProfile.setImageBitmap(bitmap);
                Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error selecting image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveUser() {
        // Get data from UI components
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();

        // Check if an image is selected
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }

        //try {
        // Convert selected image URI to bitmap
        //     Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
        // Convert bitmap to byte array
        //    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //     bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        //   byte[] imageData = outputStream.toByteArray();

        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put("Username", name);
        values.put("Email", email);
        values.put("Phone", phone);
        values.put("ProfilePicture", selectedImageUri.toString()); // Insert picture URI as string is wrong should have done by byte as saved in dbhelper as BLOB

        // Insert the data into the database
        db.insert("User", null, values);

        // Close the database
        db.close();
    }
    //catch (IOException e) {
    //  e.printStackTrace();
    //Toast.makeText(this, "Error converting image to byte array", Toast.LENGTH_SHORT).show();
    //}


    public void previous(View v) {
        Intent intent = new Intent(this, PROFILE.class);
        startActivity(intent);
    }
}


