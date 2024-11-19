package com.example.fitnesstrackingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FitnessDatabase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY, name TEXT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE meals(id INTEGER PRIMARY KEY, userId INTEGER, calories INTEGER, meal TEXT)");
        db.execSQL("CREATE TABLE workouts(id INTEGER PRIMARY KEY, userId INTEGER, workout TEXT, category TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS workouts");
        db.execSQL("DROP TABLE IF EXISTS meals");
        onCreate(db);
    }



    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{email, password});
        boolean userExists = (cursor.getCount() > 0);
        cursor.close();
        return userExists;
    }

    // Updated method to accept category instead of date
    public boolean addWorkout(int userId, String workout, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("workout", workout);
        values.put("category", category);
        long result = db.insert("workouts", null, values);
        return result != -1;
    }
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("Email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);
        return result != -1;
    }
    public Cursor getAllMeals(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM meals WHERE userId = ?", new String[]{String.valueOf(userId)});
    }
    public Cursor getAllWorkouts(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM workouts WHERE userId = ?", new String[]{String.valueOf(userId)});
    }



    public boolean addMeal(int userId, String meal, int calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("meal", meal);
        values.put("calories", calories);
        long result = db.insert("meals", null, values);

        return result != -1;
    }
}
