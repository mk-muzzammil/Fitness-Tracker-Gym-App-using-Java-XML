package com.example.fitnesstrackingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MealTracking extends AppCompatActivity {
    EditText mealEditText, caloriesEditText;
    Button saveMealButton, refreshMealsButton;
    ListView mealsListView;
    DatabaseHelper dbHelper;
    ArrayList<String> mealsList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_tracking);

        mealEditText = findViewById(R.id.editTextMeal);
        caloriesEditText = findViewById(R.id.editTextCalories);
        saveMealButton = findViewById(R.id.buttonSaveMeal);
        refreshMealsButton = findViewById(R.id.buttonRefreshMeals); // Initialize new button
        mealsListView = findViewById(R.id.listViewMeals);

        dbHelper = new DatabaseHelper(this);
        mealsList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mealsList);
        mealsListView.setAdapter(adapter);


        loadMealsFromDatabase();

        saveMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meal = mealEditText.getText().toString();
                String calories = caloriesEditText.getText().toString();

                if (!meal.isEmpty() && !calories.isEmpty()) {
                    // Assuming user ID is 1 for simplicity
                    if (dbHelper.addMeal(1, meal, Integer.parseInt(calories))) {
                        Toast.makeText(MealTracking.this, "Meal added!", Toast.LENGTH_SHORT).show();
                        mealsList.add(meal + " - " + calories + " calories");

                        mealEditText.setText(""); // Clear input fields
                        caloriesEditText.setText("");
                    } else {
                        Toast.makeText(MealTracking.this, "Failed to add meal!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MealTracking.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        refreshMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealsList.clear();
                loadMealsFromDatabase();
            }
        });
    }

    private void loadMealsFromDatabase() {
        Cursor cursor = dbHelper.getAllMeals(1); // Assuming user ID is 1
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String meal = cursor.getString(2);
                String calories = cursor.getString(3);
                mealsList.add(meal + " - " + calories + " calories");
            }
            cursor.close();
            adapter.notifyDataSetChanged(); // Notify adapter of data change
        }
    }
}
