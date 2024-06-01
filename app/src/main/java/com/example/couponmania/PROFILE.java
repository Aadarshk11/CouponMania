package com.example.couponmania;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

        import androidx.appcompat.app.AppCompatActivity;

        import java.io.FileNotFoundException;
        import java.util.ArrayList;

public class PROFILE extends AppCompatActivity {

    private TextView textViewUserName, textViewUserEmail;
    private EditText editTextUserPhone;
    private Button buttonEditProfile;
    private ImageView imageViewProfile;
    private DBHelper dbHelper;
    private ArrayList<Retrieve> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();


        textViewUserName = findViewById(R.id.textViewUserName);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        editTextUserPhone = findViewById(R.id.editTextUserPhone);
        buttonEditProfile = findViewById(R.id.buttonEditProfile);
        imageViewProfile = findViewById(R.id.imageViewProfile);

        dbHelper = new DBHelper(this,"my_database.db");

        // Fetch user data from the database
        userData = fetch_info();

        // Display the fetched data in the UI
        if (!userData.isEmpty()) {
            Retrieve user = userData.get(0);
            textViewUserName.setText(user.User);
            textViewUserEmail.setText(user.Email);
            editTextUserPhone.setText(user.Phone);

            // Load profile picture
//            String profilePictureUriString = user.Profile; // Retrieve profile picture URI from database
//            if (profilePictureUriString != null && !profilePictureUriString.isEmpty()) {
//                Uri profilePictureUri = Uri.parse(profilePictureUriString); // Convert URI string to Uri object
//                try {
//                    Bitmap profileBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(profilePictureUri));
//                    if (profileBitmap != null) {
//                        imageViewProfile.setImageBitmap(profileBitmap);
//                    } else {
//                        // Handle case where bitmap is null (e.g., failed to decode image)
//                        Toast.makeText(PROFILE.this, "Failed to load profile picture", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (FileNotFoundException e) {
//                    // Handle file not found errors
//                    e.printStackTrace();
//                    Toast.makeText(PROFILE.this, "Profile picture not found: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                // Handle case where profile picture URI is empty or null
//                Toast.makeText(this, "Profile picture URI not found!", Toast.LENGTH_SHORT).show();
//            }


        }

        // Set click listener for "Edit Profile" button
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PROFILE.this, Profile_info.class);
                startActivity(intent);
            }
        });
    }
    public ArrayList<Retrieve> fetch_info(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User" , null);
        ArrayList<Retrieve> abc = new ArrayList<>();
        if(cursor.moveToFirst()) {
            Retrieve r = new Retrieve();
            r.User = cursor.getString(1);
            r.Email = cursor.getString(2);
            r.Phone = cursor.getString(3);
            r.Profile = cursor.getString(4);

            abc.add(r);
        }
        cursor.close();
        return abc;
    }
}
