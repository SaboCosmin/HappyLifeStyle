package com.hls.happylifestyle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReplaceFood extends AppCompatActivity {
    TextView pageName,foodCalories, foodProteins, foodFiber, foodFat;
    TextView foodCarbs, foodSugar;
    AutoCompleteTextView quantityTextView;
    Food mFood;
    float foodQuantity = 100;
    private DatabaseReference mDatabaseReference;
    private ListView mFoodListView;
    final ArrayList<Food> mFoods = new ArrayList<>();
    FoodListAdapter mFoodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_food);
        foodCalories = findViewById(R.id.caloriesTextView_id);
        foodProteins = findViewById(R.id.proteinsTextView_id);
        foodCarbs = findViewById(R.id.carbsTextView_id);
        foodSugar = findViewById(R.id.sugarTextView_id);
        foodFiber = findViewById(R.id.fiberTextView_id);
        foodFat = findViewById(R.id.fatTextView_id);
        quantityTextView = findViewById(R.id.quantity_autoCompleteTextView);

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
                mFood = mFoods.get(position);
                populate();
            }
        });


    }

    public void applyQuantity(View v){
        if (quantityTextView.getText().toString() != ""){
            foodQuantity = Float.parseFloat(quantityTextView.getText().toString());
        }
        populate();
    }

    public void populate(){
        pageName.setText(mFood.getName() + "");
        foodCalories.setText(mFood.getCalories()+ "");
        foodProteins.setText(mFood.getProteins()+ "");
        foodCarbs.setText(mFood.getCarbs()+ "");
        foodSugar.setText(mFood.getSugar()+ "");
        foodFiber.setText(mFood.getFiber()+ "");
        foodFat.setText(mFood.getFat()+ "");
    }
}
