package com.hls.happylifestyle.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hls.happylifestyle.Adapters.FoodListAdapter;
import com.hls.happylifestyle.Classes.Food;
import com.hls.happylifestyle.R;

import java.util.ArrayList;

public class ReplaceFoodActivity extends AppCompatActivity {
    TextView pageName,foodCalories, foodProteins, foodFiber, foodFat;
    TextView foodCarbs, foodSugar;
    AutoCompleteTextView quantityTextView;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Food mFood;
    float foodQuantity = 1;
    private ListView mFoodListView;
    final ArrayList<Food> mFoods = new ArrayList<>();
    FoodListAdapter mFoodListAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startIntent = new Intent(getApplicationContext(), SeeMenuActivity.class);
        startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_food);

        pageName = findViewById(R.id.pageNameReplaceFood);
        foodCalories = findViewById(R.id.caloriesReplace_id);
        foodProteins = findViewById(R.id.proteinsReplace_id);
        foodCarbs = findViewById(R.id.carbsReplace_id);
        foodSugar = findViewById(R.id.sugarReplace_id);
        foodFiber = findViewById(R.id.fiberReplace_id);
        foodFat = findViewById(R.id.fatReplace_id);
        quantityTextView = findViewById(R.id.quantity_autoCompleteTextView);
        quantityTextView.setText("100");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("foods");
        mFoodListView = findViewById(R.id.foodListView);
        mFoodListAdapter = new FoodListAdapter(this, R.layout.food_list_item, mFoods);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Food value = new Food();
                for(DataSnapshot child : children){
                    value.getSnapshot(child);
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
                mFood = mFoods.get(position);
                populate();
            }
        });
    }

    public void addFood(View v){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (mFood != null) {
            switch (extras.getString("WHICH_FOOD")) {
                case ("breakfast_1"):
                    mEditor.putString(getString(R.string.breakfast1_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.breakfast1_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
                case ("breakfast_2"):
                    mEditor.putString(getString(R.string.breakfast2_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.breakfast2_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
                case ("lunch_1"):
                    mEditor.putString(getString(R.string.lunch1_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.lunch1_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
                case ("lunch_2"):
                    mEditor.putString(getString(R.string.lunch2_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.lunch2_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
                case ("dinner_1"):
                    mEditor.putString(getString(R.string.dinner1_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.dinner1_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
                case ("dinner_2"):
                    mEditor.putString(getString(R.string.dinner2_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.dinner2_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
                case ("snack1"):
                    mEditor.putString(getString(R.string.snack1_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.snack1_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
                case ("snack2"):
                    mEditor.putString(getString(R.string.snack2_name), mFood.getName());
                    mEditor.commit();
                    mEditor.putFloat(getString(R.string.snack2_weight), foodQuantity * 100);
                    mEditor.commit();
                    break;
            }
            Toast.makeText(this, "Your selected food was added.", Toast.LENGTH_SHORT).show();
        }
    }

    public void applyQuantity(View v){
        if (!quantityTextView.getText().toString().equals("")){
            Log.d("test", quantityTextView.getText().toString());
            foodQuantity = Float.parseFloat(quantityTextView.getText().toString()) / 100;
            populate();
        }

    }

    public void populate(){
        if(mFood != null) {
            pageName.setText(String.valueOf(mFood.getName()));
            foodCalories.setText(String.valueOf(foodQuantity * mFood.getCalories()));
            foodProteins.setText(String.valueOf(foodQuantity * mFood.getMacros().getProteins()));
            foodCarbs.setText(String.valueOf(foodQuantity * mFood.getMacros().getCarbohydrate().getCarbs()));
            foodSugar.setText(String.valueOf(foodQuantity * mFood.getMacros().getCarbohydrate().getSugar()));
            foodFiber.setText(String.valueOf(foodQuantity * mFood.getMacros().getCarbohydrate().getFiber()));
            foodFat.setText(String.valueOf(foodQuantity * mFood.getMacros().getFat()));
        }
    }
}
