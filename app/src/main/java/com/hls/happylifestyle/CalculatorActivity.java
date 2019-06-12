package com.hls.happylifestyle;

import android.content.Intent;
import android.os.Bundle;
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
    final ArrayList<Food> mFoods = new ArrayList<>();
    FoodListAdapter mFoodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

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
                extras.putString("FOOD_DESCRIPTION", food.getDescription());
                extras.putInt("FOOD_CALORIES", food.getCalories());
                extras.putInt("FOOD_CARBS", food.getCarbs());
                extras.putInt("FOOD_FAT", food.getFat());
                extras.putInt("FOOD_FIBER", food.getFiber());
                extras.putInt("FOOD_PRICE", food.getPrice());
                extras.putInt("FOOD_PROTEINS", food.getProteins());
                extras.putInt("FOOD_SUGAR", food.getSugar());
                extras.putInt("FOOD_TIME", food.getTimeToPrepare());

                startIntent.putExtras(extras);
                startActivity(startIntent);
            }
        });

        if (getIntent().hasExtra("pageNameActivity")) {
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }
    }
}
