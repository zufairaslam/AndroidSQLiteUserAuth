package com.android.androidsqliteuserauth;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and table information
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_REGISTER_DATE = "REGISTER_DATE";

    // Database version
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_REGISTER_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    // Upgrade the table (if the database schema is changed)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert data into the table
    public boolean insertUser(String name, String email, String password, String registerDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_REGISTER_DATE, registerDate);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // If result is -1, insert failed
    }

    // Retrieve all users
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to check if the user with the given email and password exists
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        // Check if any result is found
        boolean exists = (cursor.getCount() > 0);
        cursor.close(); // Close the cursor to release resources
        return exists;
    }
    public boolean checkUserExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to check if the user with the given email and password exists
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        // Check if any result is found
        boolean exists = (cursor.getCount() > 0);
        cursor.close(); // Close the cursor to release resources
        return exists;
    }
    @SuppressLint("Range")
    public HashMap<String, String> getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> userDetails = new HashMap<>();

        // Query to check if the user with the given email and password exists
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        // Check if a user was found
        if (cursor.moveToFirst()) {
            // Get user details from the cursor and add them to the HashMap
            userDetails.put("id", cursor.getString(cursor.getColumnIndex(COL_ID)));
            userDetails.put("name", cursor.getString(cursor.getColumnIndex(COL_NAME)));
            userDetails.put("email", cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
            userDetails.put("password", cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));

        } else {
            // If no user found, return null or an empty map
            userDetails = null;  // or use userDetails.clear() for an empty map
        }

        cursor.close();
        db.close();
        return userDetails;
    }

}