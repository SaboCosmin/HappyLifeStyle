package com.hls.happylifestyle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SeeMenuActivity extends AppCompatActivity {
    private TextView proteinsCounter, proteinsTotal, carbsCounter, carbsTotal, fatsCounter, fatsTotal;
    private TextView fiberCounter, sugarCounter,caloriesCounter, caloriesTotal;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_menu);

        initializeViews();
        populateViews();


        if (getIntent().hasExtra("pageNameActivity")){
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }
    }

    public void initializeViews(){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        proteinsCounter = findViewById(R.id.proteins_counter);
        proteinsTotal = findViewById(R.id.proteins_total);
        carbsCounter = findViewById(R.id.carbs_counter);
        carbsTotal = findViewById(R.id.carbs_total);
        fatsCounter = findViewById(R.id.fats_counter);
        fatsTotal = findViewById(R.id.fats_total);
        fiberCounter = findViewById(R.id.fiber_counter);
        sugarCounter = findViewById(R.id.sugar_counter);
        caloriesCounter = findViewById(R.id.calories_counter);
        caloriesTotal = findViewById(R.id.calories_total);
    }

    public void populateViews(){
        caloriesTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_calories_total), 1f)) + "g total");
        proteinsTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_proteins_total), 1f)) + "g total");
        fatsTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_fats_total), 1f)) + "g total");
        carbsTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_carbs_total), 1f)) + "g total");


        caloriesCounter.setText("Consumed: " + Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_calories_consumed), 0f)) + "g");
        proteinsCounter.setText("Consumed: " + Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_proteins_consumed), 0f)) + "g");
        fatsCounter.setText("Consumed: " + Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_fats_consumed), 0f)) + "g");
        carbsCounter.setText("Consumed: " + Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_carbs_consumed), 0f)) + "g");
        fiberCounter.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_fiber_consumed), 0f)) + "g");
        sugarCounter.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_sugar_consumed), 0f)) + "g");
    }

    public void eatButton(View v){
        String whatEat = v.getTag().toString();
        Log.d("test" , whatEat + " eat");

        populateViews();
    }

    public void  replaceButton(View v){
        String whatEat = v.getTag().toString();
        Log.d("test" , whatEat + " replace");
    }
}
