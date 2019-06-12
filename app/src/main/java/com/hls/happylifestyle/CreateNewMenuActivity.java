package com.hls.happylifestyle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewMenuActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private float userCaloriesAuto, userProteinsAuto, userCarbsAuto, userFatsAuto;
    TextView caloriesTextView, proteinsTextView, carbsTextView, fatsTextView;
    AutoCompleteTextView userProteinsSelect, userCarbsSelect, userFatsSelect;

    Profile userProfile;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startIntent = new Intent(getApplicationContext(), com.hls.happylifestyle.MainUserActivity.class);
        startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_menu);

        caloriesTextView = findViewById(R.id.calories_day_text);
        proteinsTextView = findViewById(R.id.proteins_day_text);
        carbsTextView = findViewById(R.id.carbs_day_text);
        fatsTextView = findViewById(R.id.fat_day_text);
        userProteinsSelect = findViewById(R.id.user_select_proteins);
        userCarbsSelect = findViewById(R.id.user_select_carbs);
        userFatsSelect = findViewById(R.id.user_select_fats);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        updateSharedPreferences();
        calculateMacros();

        caloriesTextView.setText(Float.toString(Math.round(userCaloriesAuto)));
        proteinsTextView.setText(Float.toString(Math.round(userProteinsAuto)));
        fatsTextView.setText(Float.toString(Math.round(userFatsAuto)));
        carbsTextView.setText(Float.toString(Math.round(userCarbsAuto)));

        if (getIntent().hasExtra("pageNameActivity")){
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }
    }

    private void updateSharedPreferences(){
        String name = mSharedPreferences.getString(getString(R.string.key_name), "DefaultName");
        String gender = mSharedPreferences.getString(getString(R.string.key_gender), "Male");
        int age = Integer.parseInt(mSharedPreferences.getString(getString(R.string.key_age), "30"));
        int activity_level = Integer.parseInt(mSharedPreferences.getString(getString(R.string.key_activity), "0"));
        int height = Integer.parseInt(mSharedPreferences.getString(getString(R.string.key_height), "1.80"));
        float weight = Float.parseFloat(mSharedPreferences.getString(getString(R.string.key_weight), "80"));
        String purpose = mSharedPreferences.getString(getString(R.string.key_purpose), "Better Nutrition");

        userProfile = new Profile(name, gender, age, activity_level, height, weight, purpose);
    }

    private void calculateMacros(){
        float result;
        if (userProfile.getGender().equals("Female")){
            result = 10 * userProfile.getWeight() + 6.25f * userProfile.getHeight() - 5 * userProfile.getAge() - 161;
        }else{
            result = 10 * userProfile.getWeight() + 6.25f * userProfile.getHeight() - 5 * userProfile.getAge() + 5;
        }
        switch (userProfile.getActivity_level()){
            case 0: result = result  * 1.1f;
                break;
            case 1: result = result  * 1.2f;
                break;
            case 2: result = result  * 1.4f;
                break;
            case 3: result = result  * 1.6f;
                break;
            case 4: result = result  * 1.8f;
                break;
            case 5: result = result  * 2f;
                break;
                default: result = result * 1.1f;
        }
        if (userProfile.getPurpose().equals("Gain Muscle")){
            result = result + (result * (20/100));
        }else if(userProfile.getPurpose().equals("Lose Weight")){
            result = result - (result * (20/100));
        }

        userProteinsAuto = userProfile.getWeight() * 2;
        userFatsAuto = (result * 0.25f) / 9;
        userCarbsAuto = (result - ((userProteinsAuto * 4) + (userFatsAuto * 9))) / 4;
        userCaloriesAuto = result;
    }

    public void manualCreate(View view){
        float proteins;
        if(TextUtils.isEmpty(userProteinsSelect.getText().toString())){
            proteins = userProteinsAuto;
        }else{
            proteins = Float.parseFloat(userProteinsSelect.getText().toString());
        }
        mEditor.putFloat(getString(R.string.user_key_proteins_total), proteins);
        mEditor.commit();

        float carbs;
        if(TextUtils.isEmpty(userCarbsSelect.getText().toString())){
            carbs = userCarbsAuto;
        }else{
            carbs = Float.parseFloat(userCarbsSelect.getText().toString());
        }
        mEditor.putFloat(getString(R.string.user_key_carbs_total), carbs);
        mEditor.commit();

        float fats;
        if(TextUtils.isEmpty(userFatsSelect.getText().toString())){
            fats = userFatsAuto;
        }else{
            fats = Float.parseFloat(userFatsSelect.getText().toString());
        }
        mEditor.putFloat(getString(R.string.user_key_fats_total), fats);
        mEditor.commit();

        float calories;
        calories = (proteins * 4) + (carbs * 4) + (fats * 9);
        mEditor.putFloat(getString(R.string.user_key_calories_total), calories);
        mEditor.commit();
        mEditor.putBoolean(getString(R.string.new_menu), true);
        mEditor.commit();
        mEditor.putBoolean(getString(R.string.key_menu_generated), true);
        mEditor.commit();
        Toast.makeText(this, "Your manual added macronutrients are updating... + new calories count: " + calories, Toast.LENGTH_SHORT).show();
    }
    public  void automatCreate(View view){
        mEditor.putFloat(getString(R.string.user_key_proteins_total), userProteinsAuto);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.user_key_carbs_total), userCarbsAuto);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.user_key_fats_total), userFatsAuto);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.user_key_calories_total), userCaloriesAuto);
        mEditor.commit();
        mEditor.putBoolean(getString(R.string.new_menu), true);
        mEditor.commit();
        mEditor.putBoolean(getString(R.string.key_menu_generated), true);
        mEditor.commit();
        Toast.makeText(this, "Your macronutrients are updating...", Toast.LENGTH_SHORT).show();
    }
}
