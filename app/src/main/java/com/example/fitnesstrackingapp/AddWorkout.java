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

public class AddWorkout extends AppCompatActivity {
    EditText workoutEditText, categoryEditText;
    Button saveWorkoutButton, refreshWorkoutsButton;
    ListView workoutsListView;
    DatabaseHelper dbHelper;
    ArrayList<String> workoutsList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addworkout);

        // Initialize views
        workoutEditText = findViewById(R.id.editTextWorkout);
        categoryEditText = findViewById(R.id.editTextCategory); // Updated to reference category
        saveWorkoutButton = findViewById(R.id.buttonSaveWorkout);
        refreshWorkoutsButton = findViewById(R.id.buttonRefreshWorkouts);
        workoutsListView = findViewById(R.id.listViewWorkouts);

        // Initialize database helper and ArrayList
        dbHelper = new DatabaseHelper(this);
        workoutsList = new ArrayList<>();

        // Set up the ArrayAdapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workoutsList);
        workoutsListView.setAdapter(adapter);

        // Load existing workouts from the database
        loadWorkoutsFromDatabase();

        // Set up the save button click listener
        saveWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String workout = workoutEditText.getText().toString();
                String category = categoryEditText.getText().toString(); // Updated to get category
                if (!workout.isEmpty() && !category.isEmpty()) {
                    // Assuming user ID is 1 for simplicity
                    if (dbHelper.addWorkout(1, workout, category)) { // Updated to pass category
                        Toast.makeText(AddWorkout.this, "Workout added!", Toast.LENGTH_SHORT).show();
                        workoutsList.add(workout + " - " + category); // Updated to display category
                        workoutEditText.setText("");
                        categoryEditText.setText(""); // Clear category input
                    } else {
                        Toast.makeText(AddWorkout.this, "Failed to add workout!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddWorkout.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up the refresh button click listener
        refreshWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutsList.clear(); // Clear the list before refreshing
                loadWorkoutsFromDatabase(); // Reload workouts from the database
            }
        });
    }

    public void loadWorkoutsFromDatabase() {
        Cursor cursor = dbHelper.getAllWorkouts(1); // Fetch workouts from the database
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String workout = cursor.getString(2); // Assuming workout name is in column 2
                String category = cursor.getString(3); // Assuming category is in column 1
                workoutsList.add(workout + " - " + category); // Update to show category
            }
            cursor.close(); // Close the cursor
            adapter.notifyDataSetChanged(); // Notify the adapter of data changes
        }
    }
}
