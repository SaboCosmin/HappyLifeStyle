package com.hls.happylifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class InspectFoodActivity extends AppCompatActivity {
    TextView pageName, foodDescription, foodCalories, foodProteins, foodFiber, foodFat;
    TextView foodCarbs, foodSugar, foodPrice, foodTimeToPrepare;
    AutoCompleteTextView quantityTextView;
    float foodQuantity = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_food);

        populate();

    }

    public float reconfigure(int value, float quantity){
        return (value*(quantity/100));
    }

    public String getPrice(int value){
        if(value < 5){
            return "LOW";
        }else if(value>10){
            return "HIGH";
        }else{
            return "MEDIUM";
        }
    }
    public void applyQuantity(View v){
        if (quantityTextView.getText().toString() != ""){
            foodQuantity = Float.parseFloat(quantityTextView.getText().toString());
            populate();
        }
    }

    public void populate(){
        quantityTextView = findViewById(R.id.quantity_autoCompleteTextView);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        pageName = findViewById(R.id.pageName);
        foodDescription = findViewById(R.id.descriptionTextView_id);
        foodCalories = findViewById(R.id.caloriesTextView_id);
        foodProteins = findViewById(R.id.proteinsTextView_id);
        foodCarbs = findViewById(R.id.carbsTextView_id);
        foodSugar = findViewById(R.id.sugarTextView_id);
        foodFiber = findViewById(R.id.fiberTextView_id);
        foodFat = findViewById(R.id.fatTextView_id);
        foodPrice = findViewById(R.id.priceTextView_id);
        foodTimeToPrepare = findViewById(R.id.timeTextView_id);

        pageName.setText(extras.getString("FOOD_NAME"));
        foodDescription.setText(extras.getString("FOOD_DESCRIPTION"));
        foodCalories.setText(Float.toString(reconfigure(extras.getInt("FOOD_CALORIES"), foodQuantity)));
        foodProteins.setText(Float.toString(reconfigure(extras.getInt("FOOD_PROTEINS"), foodQuantity)));
        foodCarbs.setText(Float.toString(reconfigure(extras.getInt("FOOD_CARBS"), foodQuantity)));
        foodSugar.setText(Float.toString(reconfigure(extras.getInt("FOOD_SUGAR"), foodQuantity)));
        foodFiber.setText(Float.toString(reconfigure(extras.getInt("FOOD_FIBER"), foodQuantity)));
        foodFat.setText(Float.toString(reconfigure(extras.getInt("FOOD_FAT"), foodQuantity)));
        foodPrice.setText(getPrice(extras.getInt("FOOD_PRICE")));
        foodTimeToPrepare.setText(Integer.toString(extras.getInt("FOOD_TIME")).concat(" min"));
    }
}
