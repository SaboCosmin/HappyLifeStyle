package com.hls.happylifestyle.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hls.happylifestyle.Classes.Calculator;
import com.hls.happylifestyle.Classes.Carbohydrate;
import com.hls.happylifestyle.Classes.Food;
import com.hls.happylifestyle.Adapters.FoodListAdapter;
import com.hls.happylifestyle.Classes.Macronutrient;
import com.hls.happylifestyle.R;

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity {

    private ListView mFoodListView;
    TextView proteins,fats, sugar, carbs, calories, fibers;
    final ArrayList<Food> mFoods = new ArrayList<>();
    Calculator calculatorData;
    FoodListAdapter mFoodListAdapter;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        proteins = findViewById(R.id.proteins_calculator);
        fats = findViewById(R.id.fats_calculator);
        sugar = findViewById(R.id.sugar_calculator);
        carbs = findViewById(R.id.carbs_calculator);
        calories = findViewById(R.id.calories_calculator);
        fibers = findViewById(R.id.fiber_calculator);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("foods");
        mFoodListView = findViewById(R.id.foodListView);
        mFoodListAdapter = new FoodListAdapter(this, R.layout.food_list_item, mFoods);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child : children){
                    Food value = child.getValue(Food.class);
                    mFoods.add(value);
                }
                mFoodListView.setAdapter(mFoodListAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startIntent = new Intent(getApplicationContext(), InspectFoodActivity.class);
                Bundle extras = new Bundle();
                Food food = mFoods.get(position);

                extras.putString("FOOD_NAME", food.getName());
                extras.putInt("FOOD_CALORIES", food.getCalories());
                extras.putInt("FOOD_CARBS", food.getMacros().getCarbohydrate().getCarbs());
                extras.putInt("FOOD_FAT", food.getMacros().getFat());
                extras.putInt("FOOD_FIBER", food.getMacros().getCarbohydrate().getFiber());
                extras.putInt("FOOD_PROTEINS", food.getMacros().getProteins());
                extras.putInt("FOOD_SUGAR", food.getMacros().getCarbohydrate().getSugar());

                startIntent.putExtras(extras);
                startActivity(startIntent);
                finish();
            }
        });

        restoreValues();
        getFoodValues();

    }

    public void restoreValues(){
        Macronutrient macro = new Macronutrient();
        Carbohydrate carb = new Carbohydrate();

        carb.setCarbs(mSharedPreferences.getInt(getString(R.string.carbs_calculator), 0));
        carb.setFiber(mSharedPreferences.getInt(getString(R.string.fiber_calculator), 0));
        carb.setSugar(mSharedPreferences.getInt(getString(R.string.sugar_calculator), 0));

        macro.setProteins(mSharedPreferences.getInt(getString(R.string.proteins_calculator), 0));
        macro.setFat(mSharedPreferences.getInt(getString(R.string.fats_calculator), 0));
        macro.setCarbohydrate(carb);

        calculatorData.setCalories(mSharedPreferences.getInt(getString(R.string.calories_calculator), 0));
        calculatorData.setMacronutrient(macro);
        populate();
    }

    public void getFoodValues(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        Carbohydrate carb = new Carbohydrate();
        Macronutrient macro = new Macronutrient();

        carb.setCarbs(calculatorData.getMacronutrient().getCarbohydrate().getCarbs() + extras.getInt("FOOD_CARBS"));
        carb.setSugar(calculatorData.getMacronutrient().getCarbohydrate().getSugar() + extras.getInt("FOOD_SUGAR"));
        carb.setFiber(calculatorData.getMacronutrient().getCarbohydrate().getFiber() + extras.getInt("FOOD_FIBER"));

        macro.setProteins(calculatorData.getMacronutrient().getProteins() + extras.getInt("FOOD_PROTEINS"));
        macro.setFat(calculatorData.getMacronutrient().getFat() + extras.getInt("FOOD_FAT"));
        macro.setCarbohydrate(carb);

        calculatorData.setCalories(calculatorData.getCalories() + extras.getInt("FOOD_CALORIES"));
        calculatorData.setMacronutrient(macro);
        upload();
    }

    public void resetValues(View v){

        calculatorData = new Calculator();
        upload();
    }

    public void upload(){
        mEditor.putInt(getString(R.string.proteins_calculator), calculatorData.getMacronutrient().getProteins());
        mEditor.commit();
        mEditor.putInt(getString(R.string.carbs_calculator), calculatorData.getMacronutrient().getCarbohydrate().getCarbs());
        mEditor.commit();
        mEditor.putInt(getString(R.string.fats_calculator), calculatorData.getMacronutrient().getFat());
        mEditor.commit();
        mEditor.putInt(getString(R.string.calories_calculator), calculatorData.getCalories());
        mEditor.commit();
        mEditor.putInt(getString(R.string.fiber_calculator), calculatorData.getMacronutrient().getCarbohydrate().getFiber());
        mEditor.commit();
        mEditor.putInt(getString(R.string.sugar_calculator), calculatorData.getMacronutrient().getCarbohydrate().getSugar());
        populate();
    }

    public void populate(){
        proteins.setText(calculatorData.getMacronutrient().getProteins() + " g");
        fats.setText(calculatorData.getMacronutrient().getFat() + " g");
        sugar.setText(calculatorData.getMacronutrient().getCarbohydrate().getSugar() + " g");
        carbs.setText(calculatorData.getMacronutrient().getCarbohydrate().getCarbs() + " g");
        calories.setText(calculatorData.getCalories() + " g");
        fibers.setText(calculatorData.getMacronutrient().getCarbohydrate().getFiber() + " g");
    }
}
