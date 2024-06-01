package com.example.couponmania;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    // User table
    private static final String CREATE_TABLE_USER = "CREATE TABLE User (" +
            "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Username TEXT NOT NULL," +
            "Email TEXT NOT NULL UNIQUE," +
            "Phone TEXT," +
            "ProfilePicture TEXT" +
            ");";

    // Ad table
    private static final String CREATE_TABLE_AD = "CREATE TABLE Ad (" +
            "AdID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "UserID INTEGER," +
            "Title TEXT NOT NULL," +
            "Description TEXT," +
            "Price TEXT," +
            "Location TEXT," +
            "CategoryID TEXT," +
            "ImageURLs TEXT," +
            "Code TEXT," +
            "Options TEXT," +
            "FOREIGN KEY (UserID) REFERENCES User(UserID)," +
            "FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)" +
            ");";

    // Category table
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE Category (" +
            "CategoryID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name TEXT NOT NULL," +
            "ParentCategoryID INTEGER," +
            "FOREIGN KEY (ParentCategoryID) REFERENCES Category(CategoryID)" +
            ");";

    // Favorite table
    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE Favorite (" +
            "FavoriteID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "UserID INTEGER," +
            "AdID INTEGER," +
            "FOREIGN KEY (UserID) REFERENCES User(UserID)," +
            "FOREIGN KEY (AdID) REFERENCES Ad(AdID)" +
            ");";

    // Location table
    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE Location (" +
            "LocationID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Latitude REAL," +
            "Longitude REAL," +
            "Address TEXT" +
            ");";

    public DBHelper(Context context,String databaseName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_AD);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS User;");
        db.execSQL("DROP TABLE IF EXISTS Ad;");
        db.execSQL("DROP TABLE IF EXISTS Category;");
        db.execSQL("DROP TABLE IF EXISTS Favorite;");
        db.execSQL("DROP TABLE IF EXISTS Location;");
        // Create tables again
        onCreate(db);
    }
    // Retrieve all ads as Cursor
    public Cursor getAllAdsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("Ad", null, null, null, null, null, null);
    }

}
