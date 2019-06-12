package com.hls.happylifestyle;

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

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private ListView mFoodListView;
    TextView proteins,fats, sugar, carbs, calories, fibers;
    private int proteinsCalculator,fatsCalculator, sugarCalculator, carbsCalculator, caloriesCalculator, fibersCalculator;
    final ArrayList<Food> mFoods = new ArrayList<>();
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

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("foods");
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
                extras.putInt("FOOD_CARBS", food.getCarbs());
                extras.putInt("FOOD_FAT", food.getFat());
                extras.putInt("FOOD_FIBER", food.getFiber());
                extras.putInt("FOOD_PROTEINS", food.getProteins());
                extras.putInt("FOOD_SUGAR", food.getSugar());

                startIntent.putExtras(extras);
                startActivity(startIntent);
                finish();
            }
        });

        restoreValues();
        getFoodValues();

    }

    public void restoreValues(){
        proteinsCalculator = mSharedPreferences.getInt(getString(R.string.proteins_calculator), 0);
        caloriesCalculator = mSharedPreferences.getInt(getString(R.string.calories_calculator), 0);
        carbsCalculator = mSharedPreferences.getInt(getString(R.string.carbs_calculator), 0);
        fatsCalculator = mSharedPreferences.getInt(getString(R.string.fats_calculator), 0);
        fibersCalculator = mSharedPreferences.getInt(getString(R.string.fiber_calculator), 0);
        sugarCalculator = mSharedPreferences.getInt(getString(R.string.sugar_calculator), 0);
        populate();
    }

    public void getFoodValues(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        caloriesCalculator += extras.getInt("FOOD_CALORIES");
        proteinsCalculator += extras.getInt("FOOD_PROTEINS");
        carbsCalculator += extras.getInt("FOOD_CARBS");
        sugarCalculator += extras.getInt("FOOD_SUGAR");
        fibersCalculator += extras.getInt("FOOD_FIBER");
        fatsCalculator += extras.getInt("FOOD_FAT");

        upload();
    }

    public void resetValues(View v){

        caloriesCalculator = 0;
        proteinsCalculator = 0;
        carbsCalculator = 0;
        sugarCalculator = 0;
        fibersCalculator = 0;
        fatsCalculator = 0;
        upload();
    }

    public void upload(){
        mEditor.putInt(getString(R.string.proteins_calculator), proteinsCalculator);
        mEditor.commit();
        mEditor.putInt(getString(R.string.carbs_calculator), carbsCalculator);
        mEditor.commit();
        mEditor.putInt(getString(R.string.fats_calculator), fatsCalculator);
        mEditor.commit();
        mEditor.putInt(getString(R.string.calories_calculator), caloriesCalculator);
        mEditor.commit();
        mEditor.putInt(getString(R.string.fiber_calculator), fibersCalculator);
        mEditor.commit();
        mEditor.putInt(getString(R.string.sugar_calculator), sugarCalculator);
        populate();
    }

    public void populate(){
        proteins.setText(proteinsCalculator + " g");
        fats.setText(fatsCalculator + " g");
        sugar.setText(sugarCalculator + " g");
        carbs.setText(carbsCalculator + " g");
        calories.setText(caloriesCalculator + " g");
        fibers.setText(fibersCalculator + " g");
    }
}
