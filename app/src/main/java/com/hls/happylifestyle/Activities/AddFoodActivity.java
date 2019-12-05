package com.hls.happylifestyle.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hls.happylifestyle.Classes.Carbohydrate;
import com.hls.happylifestyle.Classes.Food;
import com.hls.happylifestyle.Classes.Macronutrient;
import com.hls.happylifestyle.R;

public class AddFoodActivity extends AppCompatActivity {
    AutoCompleteTextView foodName, foodDescription, foodCalories, foodProteins, foodCarbs;
    AutoCompleteTextView foodSugar, foodFiber, foodFat;
    AutoCompleteTextView foodMinWeight,foodMaxWeight, foodGoesWith;
    Spinner foodTypeSpinner;
    CheckBox goesWithSnack, goesWithEgg, goesWithDairy, goesWithCereal, goesWithFastFood;
    CheckBox goesWithFish, goesWithSeafood, goesWithFruit, goesWithMeat, goesWithNuts, goesWithPasta;
    CheckBox goesWithRice, goesWithSoup, goesWithSalad, goesWithDesert, goesWithVegetable;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        initializeViews();

        if (getIntent().hasExtra("pageNameActivity")) {
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }

    }

    public void uploadFood(View v){
        String name = foodName.getText().toString();
        String description = foodDescription.getText().toString();
        String foodTypeText = foodTypeSpinner.getSelectedItem().toString();

        int calories = Integer.parseInt(foodCalories.getText().toString());
        int proteins = Integer.parseInt(foodProteins.getText().toString());
        int carbs = Integer.parseInt(foodCarbs.getText().toString());
        int sugar = Integer.parseInt(foodSugar.getText().toString());
        int fiber = Integer.parseInt(foodFiber.getText().toString());
        int fat = Integer.parseInt(foodFat.getText().toString());
        int price = 1;
        int timeToPrepare = 1;
        int minWeight = Integer.parseInt(foodMinWeight.getText().toString());
        int maxWeight = Integer.parseInt(foodMaxWeight.getText().toString());

        if(getGoesWith().equals("0")){
            Toast.makeText(this, "Please select one of the CheckBoxes", Toast.LENGTH_SHORT).show();
        }else{
            String goesWith = getGoesWith();
            Log.d("test", goesWith);
            Carbohydrate carbohydrate = new Carbohydrate(carbs, sugar, fiber);
            Macronutrient macronutrient = new Macronutrient(proteins, fat,carbohydrate);

            Food food = new Food(name, description, foodTypeText, goesWith, calories, price, timeToPrepare, minWeight, maxWeight,macronutrient);
            mDatabase.child("foods").push().setValue(food);
            Toast.makeText(this, "Your food was added to the Database", Toast.LENGTH_SHORT).show();
        }

    }

    public void initializeViews(){
        foodName = findViewById(R.id.name_input);
        foodDescription = findViewById(R.id.description_input);
        foodCalories = findViewById(R.id.calories_input);
        foodProteins = findViewById(R.id.proteins_input);
        foodCarbs = findViewById(R.id.carbs_input);
        foodSugar = findViewById(R.id.sugar_input);
        foodFiber = findViewById(R.id.fiber_input);
        foodFat = findViewById(R.id.fat_input);
        foodMinWeight = findViewById(R.id.min_input);
        foodMaxWeight = findViewById(R.id.max_input);

        foodTypeSpinner = findViewById(R.id.activity_level_spinner);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(AddFoodActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.food_type_spinner));

        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodTypeSpinner.setAdapter(mAdapter);

        goesWithSnack = findViewById(R.id.checkBoxSnack);
        goesWithEgg = findViewById(R.id.checkBoxEgg);
        goesWithDairy = findViewById(R.id.checkBoxDairy);
        goesWithCereal = findViewById(R.id.checkBoxCereal);
        goesWithFastFood = findViewById(R.id.checkBoxFastFood);
        goesWithFish = findViewById(R.id.checkBoxFish);
        goesWithSeafood = findViewById(R.id.checkBoxSeafood);
        goesWithFruit = findViewById(R.id.checkBoxFruit);
        goesWithMeat = findViewById(R.id.checkBoxMeat);
        goesWithNuts = findViewById(R.id.checkBoxNuts);
        goesWithPasta = findViewById(R.id.checkBoxPasta);
        goesWithRice = findViewById(R.id.checkBoxRice);
        goesWithSoup = findViewById(R.id.checkBoxSoup);
        goesWithSalad = findViewById(R.id.checkBoxSalad);
        goesWithDesert = findViewById(R.id.checkBoxDesert);
        goesWithVegetable = findViewById(R.id.checkBoxVegetable);
    }

    public String getGoesWith(){
        boolean oneIsChecked = false;
        String goesWith ="";
        if (goesWithSnack.isChecked()){
            goesWith = goesWith.concat(goesWithSnack.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithEgg.isChecked()){
            goesWith = goesWith.concat(goesWithEgg.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithDairy.isChecked()){
            goesWith = goesWith.concat(goesWithDairy.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithCereal.isChecked()){
            goesWith = goesWith.concat(goesWithCereal.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithFastFood.isChecked()){
            goesWith = goesWith.concat(goesWithFastFood.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithFish.isChecked()){
            goesWith = goesWith.concat(goesWithFish.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithSeafood.isChecked()){
            goesWith = goesWith.concat(goesWithSeafood.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithFruit.isChecked()){
            goesWith = goesWith.concat(goesWithFruit.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithMeat.isChecked()){
            goesWith = goesWith.concat(goesWithMeat.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithNuts.isChecked()){
            goesWith = goesWith.concat(goesWithNuts.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithPasta.isChecked()){
            goesWith = goesWith.concat(goesWithPasta.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithRice.isChecked()){
            goesWith = goesWith.concat(goesWithRice.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithSoup.isChecked()){
            goesWith = goesWith.concat(goesWithSoup.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithSalad.isChecked()){
            goesWith = goesWith.concat(goesWithSalad.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithDesert.isChecked()){
            goesWith = goesWith.concat(goesWithDesert.getText().toString() + ",");
            oneIsChecked = true;
        }
        if (goesWithVegetable.isChecked()){
            goesWith = goesWith.concat(goesWithVegetable.getText().toString() + ",");
            oneIsChecked = true;
        }

        if(oneIsChecked){
            return goesWith.substring(0, goesWith.length() - 1);
        }else{
            return "0";
        }
    }
}
