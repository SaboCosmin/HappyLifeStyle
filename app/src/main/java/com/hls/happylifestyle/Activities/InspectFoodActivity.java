package com.hls.happylifestyle.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.hls.happylifestyle.R;

public class InspectFoodActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    String foodName;
    TextView pageName, foodCalories, foodProteins, foodFiber, foodFat;
    TextView foodCarbs, foodSugar;
    AutoCompleteTextView quantityTextView;
    int proteinsCalculator, fatsCalculator, sugarCalculator, carbsCalculator, caloriesCalculator, fibersCalculator;
    float foodQuantity = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_food);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        getFoodValues();
        populate();

    }

    public float reconfigure(int value, float quantity){
        return (value*(quantity/100));
    }

    public void applyQuantity(View v){
        if (quantityTextView.getText().toString() != ""){
            foodQuantity = Float.parseFloat(quantityTextView.getText().toString());
            populate();
        }
    }

    public void populate(){
        quantityTextView = findViewById(R.id.quantity_autoCompleteTextView);

        pageName = findViewById(R.id.pageName);
        foodCalories = findViewById(R.id.caloriesTextView_id);
        foodProteins = findViewById(R.id.proteinsTextView_id);
        foodCarbs = findViewById(R.id.carbsTextView_id);
        foodSugar = findViewById(R.id.sugarTextView_id);
        foodFiber = findViewById(R.id.fiberTextView_id);
        foodFat = findViewById(R.id.fatTextView_id);

        pageName.setText(foodName);
        foodCalories.setText(Float.toString(reconfigure(caloriesCalculator, foodQuantity)));
        foodProteins.setText(Float.toString(reconfigure(proteinsCalculator, foodQuantity)));
        foodCarbs.setText(Float.toString(reconfigure(carbsCalculator, foodQuantity)));
        foodSugar.setText(Float.toString(reconfigure(sugarCalculator, foodQuantity)));
        foodFiber.setText(Float.toString(reconfigure(fibersCalculator, foodQuantity)));
        foodFat.setText(Float.toString(reconfigure(fatsCalculator, foodQuantity)));
    }

    public void getFoodValues(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        foodName = extras.getString("FOOD_NAME");
        caloriesCalculator = extras.getInt("FOOD_CALORIES");
        proteinsCalculator = extras.getInt("FOOD_PROTEINS");
        carbsCalculator = extras.getInt("FOOD_CARBS");
        sugarCalculator = extras.getInt("FOOD_SUGAR");
        fibersCalculator = extras.getInt("FOOD_FIBER");
        fatsCalculator = extras.getInt("FOOD_FAT");
    }

    public void addFoodCalculator(View v){
        Intent startIntent = new Intent(getApplicationContext(), CalculatorActivity.class);
        Bundle extras = new Bundle();

        extras.putString("FOOD_NAME", foodName);
        extras.putInt("FOOD_CALORIES", caloriesCalculator);
        extras.putInt("FOOD_CARBS", proteinsCalculator);
        extras.putInt("FOOD_FAT", carbsCalculator);
        extras.putInt("FOOD_FIBER", sugarCalculator);
        extras.putInt("FOOD_PROTEINS", fibersCalculator);
        extras.putInt("FOOD_SUGAR", fatsCalculator);

        startIntent.putExtras(extras);
        startActivity(startIntent);
        finish();
    }
}
