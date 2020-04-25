package com.hls.happylifestyle.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.hls.happylifestyle.Classes.Food;
import com.hls.happylifestyle.R;

public class InspectFoodActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    TextView pageName, foodCalories, foodProteins, foodFiber, foodFat;
    TextView foodCarbs, foodSugar;
    AutoCompleteTextView quantityTextView;
    Food foodCalculator;
    float foodQuantity = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_food);

        foodCalculator = new Food();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();

        getFoodValues();
        populate();

    }

    public float reconfigure(float value, float quantity) {
        return (value*(quantity/100));
    }

    public void applyQuantity(View v) {
        if (!quantityTextView.getText().toString().equals("")) {
            foodQuantity = Float.parseFloat(quantityTextView.getText().toString());
            populate();
        }
    }

    public void populate() {
        quantityTextView = findViewById(R.id.quantity_autoCompleteTextView);

        pageName = findViewById(R.id.pageName);
        foodCalories = findViewById(R.id.caloriesTextView_id);
        foodProteins = findViewById(R.id.proteinsTextView_id);
        foodCarbs = findViewById(R.id.carbsTextView_id);
        foodSugar = findViewById(R.id.sugarTextView_id);
        foodFiber = findViewById(R.id.fiberTextView_id);
        foodFat = findViewById(R.id.fatTextView_id);

        pageName.setText(foodCalculator.getName());
        foodCalories.setText(String.valueOf(reconfigure(foodCalculator.getMacros().getProteins(), foodQuantity)));
        foodProteins.setText(String.valueOf(reconfigure(foodCalculator.getMacros().getProteins(), foodQuantity)));
        foodCarbs.setText(String.valueOf(reconfigure(foodCalculator.getMacros().getCarbohydrate().getCarbs(), foodQuantity)));
        foodSugar.setText(String.valueOf(reconfigure(foodCalculator.getMacros().getCarbohydrate().getSugar(), foodQuantity)));
        foodFiber.setText(String.valueOf(reconfigure(foodCalculator.getMacros().getCarbohydrate().getFiber(), foodQuantity)));
        foodFat.setText(String.valueOf(reconfigure(foodCalculator.getMacros().getFat(), foodQuantity)));
    }

    public void getFoodValues() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        foodCalculator.setName(extras.getString("FOOD_NAME"));
        foodCalculator.setCalories(extras.getFloat("FOOD_CALORIES"));
        foodCalculator.getMacros().setProteins(extras.getFloat("FOOD_PROTEINS"));
        foodCalculator.getMacros().getCarbohydrate().setCarbs(extras.getFloat("FOOD_CARBS"));
        foodCalculator.getMacros().getCarbohydrate().setSugar(extras.getFloat("FOOD_SUGAR"));
        foodCalculator.getMacros().getCarbohydrate().setFiber(extras.getFloat("FOOD_FIBER"));
        foodCalculator.getMacros().setFat(extras.getFloat("FOOD_FAT"));

    }

    public void addFoodCalculator(View v) {
        Intent startIntent = new Intent(getApplicationContext(), CalculatorActivity.class);
        Bundle extras = new Bundle();

        extras.putString("FOOD_NAME", foodCalculator.getName());
        extras.putFloat("FOOD_CALORIES", foodCalculator.getCalories());
        extras.putFloat("FOOD_CARBS", foodCalculator.getMacros().getProteins());
        extras.putFloat("FOOD_FAT", foodCalculator.getMacros().getCarbohydrate().getCarbs());
        extras.putFloat("FOOD_FIBER", foodCalculator.getMacros().getCarbohydrate().getSugar());
        extras.putFloat("FOOD_PROTEINS", foodCalculator.getMacros().getCarbohydrate().getFiber());
        extras.putFloat("FOOD_SUGAR", foodCalculator.getMacros().getFat());

        startIntent.putExtras(extras);
        startActivity(startIntent);
        finish();
    }
}
