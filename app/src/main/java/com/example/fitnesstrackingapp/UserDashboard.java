
package com.example.fitnesstrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDashboard extends AppCompatActivity {
    ListView workoutRecyclerView;
    Button addWorkoutButton, viewMealsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdashboard);

        workoutRecyclerView = findViewById(R.id.listViewWorkouts);
        addWorkoutButton = findViewById(R.id.buttonAddWorkout);
        viewMealsButton = findViewById(R.id.buttonViewMeals);

        // Set up RecyclerView and load workouts from database...

        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, AddWorkout.class);
                startActivity(intent);
            }
        });

        viewMealsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, MealTracking.class);
                startActivity(intent);
            }
        });

    }
}
