package com.hls.happylifestyle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFoodActivity extends AppCompatActivity {
    AutoCompleteTextView foodName, foodDescription, foodCalories, foodProteins, foodCarbs;
    AutoCompleteTextView foodSugar, foodFiber, foodFat, foodPrice, foodTimeToPrepare;
    AutoCompleteTextView foodMinWeight,foodMaxWeight, foodType, foodGoesWith;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        initializeViews();

        if (getIntent().hasExtra("pageNameActivity")) {
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }

    }

    public void uploadFood(View v){
        String name = foodName.getText().toString();
        String description = foodDescription.getText().toString();
        String type = foodType.getText().toString();
        String goesWith = foodGoesWith.getText().toString();
        int calories = Integer.parseInt(foodCalories.getText().toString());
        int proteins = Integer.parseInt(foodProteins.getText().toString());
        int carbs = Integer.parseInt(foodCarbs.getText().toString());
        int sugar = Integer.parseInt(foodSugar.getText().toString());
        int fiber = Integer.parseInt(foodFiber.getText().toString());
        int fat = Integer.parseInt(foodFat.getText().toString());
        int price = Integer.parseInt(foodPrice.getText().toString());
        int timeToPrepare = Integer.parseInt(foodTimeToPrepare.getText().toString());
        int min = Integer.parseInt(foodMinWeight.getText().toString());
        int max = Integer.parseInt(foodMaxWeight.getText().toString());

        Food food = new Food(name, description, type, goesWith, calories, proteins,
                                carbs, sugar, fiber, fat, price, timeToPrepare, min, max);
        mDatabase.child("foods").push().setValue(food);

        Toast.makeText(this, "Your food was added to the Database", Toast.LENGTH_SHORT).show();
    }

    public void initializeViews(){
        foodName = findViewById(R.id.name_input);
        foodDescription = findViewById(R.id.description_input);
        foodCalories = findViewById(R.id.calories_input);
        foodProteins = findViewById(R.id.proteins_input);
        foodCarbs = findViewById(R.id.carbs_input);
        foodSugar = findViewById(R.id.sugar_input);
        foodFiber = findViewById(R.id.fiber_input);
        foodFat = findViewById(R.id.fat_input);
        foodPrice = findViewById(R.id.price_input);
        foodTimeToPrepare = findViewById(R.id.timeToPrepare_input);
        foodMinWeight = findViewById(R.id.min_input);
        foodMaxWeight = findViewById(R.id.max_input);
        foodType = findViewById(R.id.type_input);
        foodGoesWith = findViewById(R.id.goesWith_input);
    }
}
