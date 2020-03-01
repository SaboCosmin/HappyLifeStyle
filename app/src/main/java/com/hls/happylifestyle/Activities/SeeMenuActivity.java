package com.hls.happylifestyle.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hls.happylifestyle.Classes.Food;
import com.hls.happylifestyle.R;

import java.util.ArrayList;
import java.util.Random;

public class SeeMenuActivity extends AppCompatActivity {
    private TextView proteinsCounter, proteinsTotal, carbsCounter, carbsTotal, fatsCounter, fatsTotal;
    private TextView fiberCounter, sugarCounter, caloriesCounter, caloriesTotal;
    private TextView snack1_tv, snack2_tv, breakfast1_tv, breakfast2_tv, lunch1_tv, lunch2_tv, dinner1_tv, dinner2_tv;
    private TextView snack1_g_tv, snack2_g_tv, breakfast1_g_tv, breakfast2_g_tv, lunch1_g_tv, lunch2_g_tv, dinner1_g_tv, dinner2_g_tv;
    private ArrayList<Food> snack, egg, dairy, cereal, fastFood, fish, seafood, fruit;
    private ArrayList<Food> meat, nuts, pasta, rice, salad, soup, desert, vegetable;
    private ArrayList<Food> foodsInMenu;
    private Food snack1, snack2, breakfast1, breakfast2, lunch1, lunch2, dinner1, dinner2;
    private float snack1_g, snack2_g, breakfast1_g, breakfast2_g, lunch1_g, lunch2_g, dinner1_g, dinner2_g;
    private float proteinsUsed, carbsUsed, fatsUsed, caloriesUsed, fiberUsed, sugarUsed;
    private float totalProteins, totalFats, totalCarbs;
    private Button breakfastEatBtn, snack1EatBtn, snack2EatBtn, lunchEatBtn, dinnerEatBtn;

    final ArrayList<Food> mFoods = new ArrayList<>();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_menu);

        if (getIntent().hasExtra("pageNameActivity")) {
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("foods");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Food value = new Food();
                for (DataSnapshot child : children) {
                    value.getSnapshot(child);
                    mFoods.add(value);
                }
                for (Food food : mFoods) {
                    putFood(food, food.getType());
                }
                if (mSharedPreferences.getBoolean(getString(R.string.new_menu), true)) {
                    generateMenu();
                    mEditor.putBoolean(getString(R.string.new_menu), false);
                    mEditor.apply();
                } else {
                    getGeneratedMenu();
                }

                initializeViews();
                populateViews();
                populateMenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        initializeFood();
    }

    public void initializeViews() {
        breakfastEatBtn = findViewById(R.id.add_breakfast);
        snack1EatBtn = findViewById(R.id.add_snack_1);
        snack2EatBtn = findViewById(R.id.add_snack_2);
        dinnerEatBtn = findViewById(R.id.add_dinner);
        lunchEatBtn = findViewById(R.id.add_lunch);

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

        breakfast1_tv = findViewById(R.id.breakfast1_name);
        breakfast2_tv = findViewById(R.id.breakfast2_name);
        breakfast1_g_tv = findViewById(R.id.breakfast1_weight);
        breakfast2_g_tv = findViewById(R.id.breakfast2_weight);

        lunch1_tv = findViewById(R.id.lunch1_name);
        lunch2_tv = findViewById(R.id.lunch2_name);
        lunch1_g_tv = findViewById(R.id.lunch1_weight);
        lunch2_g_tv = findViewById(R.id.lunch2_weight);

        dinner1_tv = findViewById(R.id.dinner1_name);
        dinner2_tv = findViewById(R.id.dinner2_name);
        dinner1_g_tv = findViewById(R.id.dinner1_weight);
        dinner2_g_tv = findViewById(R.id.dinner2_weight);

        snack1_tv = findViewById(R.id.snack1_name);
        snack2_tv = findViewById(R.id.snack2_name);
        snack1_g_tv = findViewById(R.id.snack1_weight);
        snack2_g_tv = findViewById(R.id.snack2_weight);
    }

    public void populateMenu() {
        breakfast1_tv.setText(breakfast1.getName());
        breakfast2_tv.setText(breakfast2.getName());
        breakfast1_g_tv.setText(Math.round(breakfast1_g) + "g");
        breakfast2_g_tv.setText(Math.round(breakfast2_g) + "g");

        lunch1_tv.setText(lunch1.getName());
        lunch2_tv.setText(lunch2.getName());
        lunch1_g_tv.setText(Math.round(lunch1_g) + "g");
        lunch2_g_tv.setText(Math.round(lunch2_g) + "g");

        dinner1_tv.setText(dinner1.getName());
        dinner2_tv.setText(dinner2.getName());
        dinner1_g_tv.setText(Math.round(dinner1_g) + "g");
        dinner2_g_tv.setText(Math.round(dinner2_g) + "g");

        snack1_tv.setText(snack1.getName());
        snack2_tv.setText(snack2.getName());
        snack1_g_tv.setText(Math.round(snack1_g) + "g");
        snack2_g_tv.setText(Math.round(snack2_g) + "g");
    }

    public void populateViews() {

        if (!mSharedPreferences.getBoolean(getString(R.string.key_breakfast_consumed), false)) {
            breakfastEatBtn.setEnabled(true);
        } else {
            breakfastEatBtn.setEnabled(false);
        }
        if (!mSharedPreferences.getBoolean(getString(R.string.key_snack1_consumed), false)) {
            snack1EatBtn.setEnabled(true);
        } else {
            snack1EatBtn.setEnabled(false);
        }
        if (!mSharedPreferences.getBoolean(getString(R.string.key_snack2_consumed), false)) {
            snack2EatBtn.setEnabled(true);
        } else {
            snack2EatBtn.setEnabled(false);
        }
        if (!mSharedPreferences.getBoolean(getString(R.string.key_lunch_consumed), false)) {
            lunchEatBtn.setEnabled(true);
        } else {
            lunchEatBtn.setEnabled(false);
        }
        if (!mSharedPreferences.getBoolean(getString(R.string.key_dinner_consumed), false)) {
            dinnerEatBtn.setEnabled(true);
        } else {
            dinnerEatBtn.setEnabled(false);
        }


        caloriesTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_calories_total), 1f)) + "g total");
        totalProteins = mSharedPreferences.getFloat(getString(R.string.user_key_proteins_total), 1f);
        proteinsTotal.setText(Math.round(totalProteins) + "g total");
        totalFats = mSharedPreferences.getFloat(getString(R.string.user_key_fats_total), 1f);
        fatsTotal.setText(Math.round(totalFats) + "g total");
        totalCarbs = mSharedPreferences.getFloat(getString(R.string.user_key_carbs_total), 1f);
        carbsTotal.setText(Math.round(totalCarbs) + "g total");


        if (mSharedPreferences.getBoolean(getString(R.string.key_dinner_consumed), false) && mSharedPreferences.getBoolean(getString(R.string.key_lunch_consumed), false)
                && mSharedPreferences.getBoolean(getString(R.string.key_snack2_consumed), false) && mSharedPreferences.getBoolean(getString(R.string.key_snack1_consumed), false)
                && mSharedPreferences.getBoolean(getString(R.string.key_breakfast_consumed), false)
        ) {
            Random random = new Random();
            int randomP = random.nextInt(20);
            int randomC = random.nextInt(20);
            int randomF = random.nextInt(20);
            proteinsCounter.setText("Proteins consumed: " + Math.round(totalProteins + randomP) + "g");
            fatsCounter.setText("Fats consumed: " + Math.round(totalFats + randomF) + "g");
            carbsCounter.setText("Carbs consumed: " + Math.round(totalCarbs - randomC) + "g");
            caloriesCounter.setText("Calories consumed: " + (Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_calories_total), 1f)) + (randomP * 4 + randomF * 9 - randomC * 4)) + "g");
            fiberCounter.setText(Math.round(fiberUsed) + "g");
            sugarCounter.setText(Math.round(sugarUsed) + "g");
        } else {
            caloriesCounter.setText("Calories consumed: " + Math.round(caloriesUsed) + "g");
            proteinsCounter.setText("Proteins consumed: " + Math.round(proteinsUsed) + "g");
            fatsCounter.setText("Fats consumed: " + Math.round(fatsUsed) + "g");
            carbsCounter.setText("Carbs consumed: " + Math.round(carbsUsed) + "g");
            fiberCounter.setText(Math.round(fiberUsed) + "g");
            sugarCounter.setText(Math.round(sugarUsed) + "g");
        }

    }

    public void generateMenu() {
        float proteinsTotal, fatsTotal, carbsTotal;
        proteinsTotal = mSharedPreferences.getFloat(getString(R.string.user_key_proteins_total), 1f);
        fatsTotal = mSharedPreferences.getFloat(getString(R.string.user_key_fats_total), 1f);
        carbsTotal = mSharedPreferences.getFloat(getString(R.string.user_key_carbs_total), 1f);
        generateDinner(Math.round(proteinsTotal * 0.35f), Math.round(carbsTotal * 0.35f), Math.round(fatsTotal * 0.35f));
        generateBreakfast(Math.round(proteinsTotal * 0.35f), Math.round(carbsTotal * 0.35f), Math.round(fatsTotal * 0.35f));
        generateLaunch(Math.round(proteinsTotal * 0.4f), Math.round(carbsTotal * 0.4f), Math.round(fatsTotal * 0.4f));
        generateSnack(Math.round(totalProteins - getMainMenuProteins()), Math.round(totalCarbs - getMainMenuCarbs()), Math.round(totalFats - getMainMenuFats()));
        saveMenu();
    }

    private void generateSnack(int proteins, int carbs, int fats) {
        snack1 = getRandomFood(snack);
        snack2 = getRandomFood(snack);
        int[] weight = setValues(snack1, snack2, proteins, carbs, fats);
        snack1_g = weight[0];
        snack2_g = weight[1];
    }

    private void initializeFood() {
        snack = new ArrayList<>();
        egg = new ArrayList<>();
        dairy = new ArrayList<>();
        cereal = new ArrayList<>();
        fastFood = new ArrayList<>();
        fish = new ArrayList<>();
        seafood = new ArrayList<>();
        fruit = new ArrayList<>();
        meat = new ArrayList<>();
        nuts = new ArrayList<>();
        pasta = new ArrayList<>();
        rice = new ArrayList<>();
        salad = new ArrayList<>();
        soup = new ArrayList<>();
        desert = new ArrayList<>();
        vegetable = new ArrayList<>();
        foodsInMenu = new ArrayList<>();

    }

    private void generateDinner(int proteins, int carbs, int fats) {
        String[] is = {"salad", "fish", "meat", "pasta", "rice", "vegetable"};
        dinner1 = getRandomFood(getRandomList(is));
        while (foodInMenu(dinner1)) {
            dinner1 = getRandomFood(getRandomList(is));
        }
        Log.d("test", dinner1.getGoesWith());
        String[] goesWith = dinner1.getGoesWith().split(",");
        dinner2 = getRandomFood(getRandomList(goesWith));
        while (foodInMenu(dinner2)) {
            dinner2 = getRandomFood(getRandomList(is));
        }
        int[] weight = setValues(dinner1, dinner2, proteins, carbs, fats);
        dinner1_g = weight[0];
        dinner2_g = weight[1];
    }

    private void generateBreakfast(int proteins, int carbs, int fats) {
        String[] is = {"egg", "dairy", "cereal", "nuts", "salad"};
        breakfast1 = getRandomFood(getRandomList(is));
        while (foodInMenu(breakfast1)) {
            breakfast1 = getRandomFood(getRandomList(is));
        }
        Log.d("test", breakfast1.getGoesWith());
        String[] goesWith = breakfast1.getGoesWith().split(",");
        breakfast2 = getRandomFood(getRandomList(goesWith));
        while (foodInMenu(breakfast2)) {
            breakfast2 = getRandomFood(getRandomList(is));
        }
        int[] weight = setValues(breakfast1, breakfast2, proteins, carbs, fats);
        breakfast1_g = weight[0];
        breakfast2_g = weight[1];
    }

    private void generateLaunch(int proteins, int carbs, int fats) {
        String[] is = {"fish", "meat", "pasta", "rice", "salad"};
        lunch1 = getRandomFood(getRandomList(is));
        while (foodInMenu(lunch1)) {
            lunch1 = getRandomFood(getRandomList(is));
        }
        Log.d("test", lunch1.getGoesWith());
        String[] goesWith = lunch1.getGoesWith().split(",");
        lunch2 = getRandomFood(getRandomList(goesWith));
        while (foodInMenu(lunch2)) {
            lunch2 = getRandomFood(getRandomList(is));
        }
        int[] weight = setValues(lunch1, lunch2, proteins, carbs, fats);
        lunch1_g = weight[0];
        lunch2_g = weight[1];
    }

    public void eatButton(View v) {
        String whatEat = v.getTag().toString();
        switch (whatEat) {
            case ("breakfast"):
                proteinsUsed = proteinsUsed + (breakfast1.getMacros().getProteins() * breakfast1_g + breakfast2.getMacros().getProteins() * breakfast2_g) / 100;
                carbsUsed = carbsUsed + (breakfast1.getMacros().getCarbohydrate().getCarbs() * breakfast1_g + breakfast2.getMacros().getCarbohydrate().getCarbs() * breakfast2_g) / 100;
                fatsUsed = fatsUsed + (breakfast1.getMacros().getFat() * breakfast1_g + breakfast2.getMacros().getFat() * breakfast2_g) / 100;
                fiberUsed = fiberUsed + (breakfast1.getMacros().getCarbohydrate().getFiber() * breakfast1_g + breakfast2.getMacros().getCarbohydrate().getFiber() * breakfast2_g) / 100;
                sugarUsed = fiberUsed + (breakfast1.getMacros().getCarbohydrate().getSugar() * breakfast1_g + breakfast2.getMacros().getCarbohydrate().getSugar() * breakfast2_g) / 100;
                mEditor.putBoolean(getString(R.string.key_breakfast_consumed), true);
                mEditor.commit();
                break;
            case ("lunch"):
                proteinsUsed = proteinsUsed + (lunch1.getMacros().getProteins() * lunch1_g + lunch2.getMacros().getProteins() * lunch2_g) / 100;
                carbsUsed = carbsUsed + (lunch1.getMacros().getCarbohydrate().getCarbs() * lunch1_g + lunch2.getMacros().getCarbohydrate().getCarbs() * lunch2_g) / 100;
                fatsUsed = fatsUsed + (lunch1.getMacros().getFat() * lunch1_g + lunch2.getMacros().getFat() * lunch2_g) / 100;
                fiberUsed = fiberUsed + (lunch1.getMacros().getCarbohydrate().getFiber() * lunch1_g + lunch2.getMacros().getCarbohydrate().getFiber() * lunch2_g) / 100;
                sugarUsed = sugarUsed + (lunch1.getMacros().getCarbohydrate().getSugar() * lunch1_g + lunch2.getMacros().getCarbohydrate().getSugar() * lunch2_g) / 100;
                mEditor.putBoolean(getString(R.string.key_lunch_consumed), true);
                mEditor.commit();
                break;
            case ("dinner"):
                proteinsUsed = proteinsUsed + (dinner1.getMacros().getProteins() * dinner1_g + dinner2.getMacros().getProteins() * dinner2_g) / 100;
                carbsUsed = carbsUsed + (dinner1.getMacros().getCarbohydrate().getCarbs() * dinner1_g + dinner2.getMacros().getCarbohydrate().getCarbs() * dinner2_g) / 100;
                fatsUsed = fatsUsed + (dinner1.getMacros().getFat() * dinner1_g + dinner2.getMacros().getFat() * dinner2_g) / 100;
                fiberUsed = fiberUsed + (dinner1.getMacros().getCarbohydrate().getFiber() * dinner1_g + dinner2.getMacros().getCarbohydrate().getFiber() * dinner2_g) / 100;
                sugarUsed = sugarUsed + (dinner1.getMacros().getCarbohydrate().getSugar() * dinner1_g + dinner2.getMacros().getCarbohydrate().getSugar() * dinner2_g) / 100;
                mEditor.putBoolean(getString(R.string.key_dinner_consumed), true);
                mEditor.commit();
                break;
            case ("snack1"):
                proteinsUsed = proteinsUsed + (snack1.getMacros().getProteins() * snack1_g) / 100;
                carbsUsed = carbsUsed + (snack1.getMacros().getCarbohydrate().getCarbs() * snack1_g) / 100;
                fatsUsed = fatsUsed + (snack1.getMacros().getFat() * snack1_g) / 100;
                fiberUsed = fiberUsed + (snack1.getMacros().getCarbohydrate().getFiber() * snack1_g) / 100;
                sugarUsed = sugarUsed + (snack1.getMacros().getCarbohydrate().getSugar() * snack1_g) / 100;
                mEditor.putBoolean(getString(R.string.key_snack1_consumed), true);
                mEditor.commit();
                break;
            case ("snack2"):
                proteinsUsed = proteinsUsed + (snack2.getMacros().getProteins() * snack2_g) / 100;
                carbsUsed = carbsUsed + (snack2.getMacros().getCarbohydrate().getCarbs() * snack2_g) / 100;
                fatsUsed = fatsUsed + (snack2.getMacros().getFat() * snack2_g) / 100;
                fiberUsed = fiberUsed + (snack2.getMacros().getCarbohydrate().getFiber() * snack2_g) / 100;
                sugarUsed = sugarUsed + (snack2.getMacros().getCarbohydrate().getSugar() * snack2_g) / 100;
                mEditor.putBoolean(getString(R.string.key_snack2_consumed), true);
                mEditor.commit();
                break;
            default:
                break;
        }
        caloriesUsed = (proteinsUsed * 4 + carbsUsed * 4 + fatsUsed * 9);
        saveMenu();
        populateViews();
    }

    public void putFood(Food food, String type) {
        switch (type) {
            case ("snack"):
                snack.add(food);
                break;
            case ("egg"):
                egg.add(food);
                break;
            case ("dairy"):
                dairy.add(food);
                break;
            case ("cereal"):
                cereal.add(food);
                break;
            case ("fastFood"):
                fastFood.add(food);
                break;
            case ("fish"):
                fish.add(food);
                break;
            case ("seafood"):
                seafood.add(food);
                break;
            case ("fruit"):
                fruit.add(food);
                break;
            case ("meat"):
                meat.add(food);
                break;
            case ("nuts"):
                nuts.add(food);
                break;
            case ("rice"):
                rice.add(food);
                break;
            case ("pasta"):
                pasta.add(food);
                break;
            case ("salad"):
                salad.add(food);
                break;
            case ("soup"):
                soup.add(food);
                break;
            case ("desert"):
                desert.add(food);
                break;
            case ("vegetable"):
                vegetable.add(food);
                break;
            default:
                Log.d("noMatch", "no match");
        }
    }

    public void replaceButton(View v) {
        final Intent startIntent = new Intent(getApplicationContext(), ReplaceFoodActivity.class);
        final String whatEat = v.getTag().toString();
        final Bundle extras = new Bundle();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        extras.putString("WHICH_FOOD", whatEat + "_2");
                        startIntent.putExtras(extras);
                        startActivity(startIntent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        extras.putString("WHICH_FOOD", whatEat + "_1");
                        startIntent.putExtras(extras);
                        startActivity(startIntent);
                        finish();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (whatEat) {
            case ("breakfast"):
                builder.setMessage("Chose which food to replace: ").setPositiveButton(breakfast2.getName(), dialogClickListener)
                        .setNegativeButton(breakfast1.getName(), dialogClickListener).show();
                break;
            case ("lunch"):
                builder.setMessage("Chose which food to replace: ").setPositiveButton(lunch2.getName(), dialogClickListener)
                        .setNegativeButton(lunch1.getName(), dialogClickListener).show();
                break;
            case ("dinner"):
                builder.setMessage("Chose which food to replace: ").setPositiveButton(dinner2.getName(), dialogClickListener)
                        .setNegativeButton(dinner1.getName(), dialogClickListener).show();
                break;
            case ("snack1"):
                extras.putString("WHICH_FOOD", "snack1");
                startIntent.putExtras(extras);
                startActivity(startIntent);
                finish();
                break;
            case ("snack2"):
                extras.putString("WHICH_FOOD", "snack2");
                startIntent.putExtras(extras);
                startActivity(startIntent);
                finish();
                break;
        }

    }

    public ArrayList<Food> getRandomList(String[] goesWith) {
        Random random = new Random();
        int randNum;
        if (goesWith.length == 1) {
            randNum = 0;
        } else {
            randNum = random.nextInt(goesWith.length - 1);
        }
        if (goesWith[randNum].equals("snack")) {
            return snack;
        }
        if (goesWith[randNum].equals("egg")) {
            return egg;
        }
        if (goesWith[randNum].equals("dairy")) {
            return dairy;
        }
        if (goesWith[randNum].equals("cereal")) {
            return cereal;
        }
        if (goesWith[randNum].equals("fast food")) {
            return fastFood;
        }
        if (goesWith[randNum].equals("seafood")) {
            return seafood;
        }
        if (goesWith[randNum].equals("fish")) {
            return fish;
        }
        if (goesWith[randNum].equals("fruit")) {
            return fruit;
        }
        if (goesWith[randNum].equals("meat")) {
            return meat;
        }
        if (goesWith[randNum].equals("nuts")) {
            return nuts;
        }
        if (goesWith[randNum].equals("rice")) {
            return rice;
        }
        if (goesWith[randNum].equals("pasta")) {
            return pasta;
        }
        if (goesWith[randNum].equals("salad")) {
            return salad;
        }
        if (goesWith[randNum].equals("soup")) {
            return soup;
        }
        if (goesWith[randNum].equals("desert")) {
            return desert;
        }
        if (goesWith[randNum].equals("vegetable")) {
            return vegetable;
        } else {
            return meat;
        }
    }

    public Food getRandomFood(ArrayList<Food> foods) {
        Random random = new Random();
        int randNum;
        if (foods.size() == 1) {
            randNum = 0;
        } else {
            randNum = random.nextInt(foods.size());
        }
        return foods.get(randNum);
    }

    public int[] setValues(Food f1, Food f2, int proteins, int carbs, int fats) {
        int weightF1, weightF2;
        weightF1 = Math.round((f1.getMinWeight() + f1.getMaxWeight()) / 2);
        weightF2 = Math.round((f2.getMinWeight() + f2.getMaxWeight()) / 2);

        for (int i = 0; i < 10; i++) {
            if (f1.getMacros().getFat() != 0 || f2.getMacros().getFat() != 0) {
                if (f1.getMacros().getFat() > f2.getMacros().getFat()) {
                    while (((f1.getMacros().getFat() * weightF1) / 100 + (f2.getMacros().getFat() * weightF2) / 100) < fats && weightF1 < f1.getMaxWeight() && weightF2 < f2.getMaxWeight()) {
                        weightF1 += 30;
                        weightF2 += 10;
                    }
                    while (((f1.getMacros().getFat() * weightF1) / 100 + (f2.getMacros().getFat() * weightF2) / 100) > fats && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF1 -= 10;
                        weightF2 -= 5;
                    }
                } else {
                    while (((f1.getMacros().getFat() * weightF1) / 100 + (f2.getMacros().getFat() * weightF2) / 100) < fats && weightF1 < f1.getMaxWeight() && weightF2 < f2.getMaxWeight()) {
                        weightF2 += 30;
                        weightF1 += 10;
                    }
                    while (((f1.getMacros().getFat() * weightF1) / 100 + (f2.getMacros().getFat() * weightF2) / 100) > fats && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF2 -= 10;
                        weightF1 -= 5;
                    }
                }
            }

            if (f1.getMacros().getProteins() != 0 || f2.getMacros().getProteins() != 0) {
                if (f1.getMacros().getProteins() > f2.getMacros().getProteins()) {
                    while (((f1.getMacros().getProteins() * weightF1) / 100 + (f2.getMacros().getProteins() * weightF2) / 100) < proteins && weightF1 < f1.getMaxWeight() && weightF2 < f2.getMaxWeight()) {
                        weightF1 += 30;
                        weightF2 += 10;
                    }
                    while (((f1.getMacros().getProteins() * weightF1) / 100 + (f2.getMacros().getProteins() * weightF2) / 100) > proteins && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF1 -= 10;
                        weightF2 -= 5;
                    }
                } else {
                    while (((f1.getMacros().getProteins() * weightF1) / 100 + (f2.getMacros().getProteins() * weightF2) / 100) < proteins && weightF1 < f1.getMaxWeight() && weightF2 < f2.getMaxWeight()) {
                        weightF2 += 30;
                        weightF1 += 10;
                    }
                    while (((f1.getMacros().getProteins() * weightF1) / 100 + (f2.getMacros().getProteins() * weightF2) / 100) > proteins && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF2 -= 10;
                        weightF1 -= 5;
                    }
                }
            }

            if (f1.getMacros().getCarbohydrate().getCarbs() != 0 || f2.getMacros().getCarbohydrate().getCarbs() != 0) {
                if (f1.getMacros().getCarbohydrate().getCarbs() > f2.getMacros().getCarbohydrate().getCarbs()) {
                    while (((f1.getMacros().getCarbohydrate().getCarbs() * weightF1) / 100 + (f2.getMacros().getCarbohydrate().getCarbs() * weightF2) / 100) < carbs && weightF1 < f1.getMaxWeight() && weightF2 < f2.getMaxWeight()) {
                        weightF1 += 30;
                        weightF2 += 10;
                    }
                    while (((f1.getMacros().getCarbohydrate().getCarbs() * weightF1) / 100 + (f2.getMacros().getCarbohydrate().getCarbs() * weightF2) / 100) > carbs && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF1 -= 10;
                        weightF2 -= 5;
                    }
                } else {
                    while (((f1.getMacros().getCarbohydrate().getCarbs() * weightF1) / 100 + (f2.getMacros().getCarbohydrate().getCarbs() * weightF2) / 100) < carbs && weightF1 < f1.getMaxWeight() && weightF2 < f2.getMaxWeight()) {
                        weightF2 += 30;
                        weightF1 += 10;
                    }
                    while (((f1.getMacros().getCarbohydrate().getCarbs() * weightF1) / 100 + (f2.getMacros().getCarbohydrate().getCarbs() * weightF2) / 100) > carbs && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF2 -= 10;
                        weightF1 -= 5;
                    }
                }
            }
        }
        return new int[]{weightF1, weightF2};
    }

    public void saveMenu() {

        mEditor.putFloat(getString(R.string.proteins_used_weight), proteinsUsed);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.carbs_used_weight), carbsUsed);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.fats_used_weight), fatsUsed);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.calories_used_weight), caloriesUsed);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.fiber_used_weight), fiberUsed);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.sugar_used_weight), sugarUsed);
        mEditor.commit();

        mEditor.putString(getString(R.string.snack1_name), snack1.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.snack2_name), snack2.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.breakfast1_name), breakfast1.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.breakfast2_name), breakfast2.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.lunch1_name), lunch1.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.lunch2_name), lunch2.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.dinner1_name), dinner1.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.dinner2_name), dinner2.getName());
        mEditor.commit();

        mEditor.putFloat(getString(R.string.snack1_weight), snack1_g);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.snack2_weight), snack2_g);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.breakfast1_weight), breakfast1_g);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.breakfast2_weight), breakfast2_g);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.lunch1_weight), lunch1_g);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.lunch2_weight), lunch2_g);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.dinner1_weight), dinner1_g);
        mEditor.commit();
        mEditor.putFloat(getString(R.string.dinner2_weight), dinner2_g);
        mEditor.commit();

    }

    public void getGeneratedMenu() {

        proteinsUsed = mSharedPreferences.getFloat(getString(R.string.proteins_used_weight), 0);
        carbsUsed = mSharedPreferences.getFloat(getString(R.string.carbs_used_weight), 0);
        fatsUsed = mSharedPreferences.getFloat(getString(R.string.fats_used_weight), 0);
        caloriesUsed = mSharedPreferences.getFloat(getString(R.string.calories_used_weight), 0);
        fiberUsed = mSharedPreferences.getFloat(getString(R.string.fiber_used_weight), 0);
        sugarUsed = mSharedPreferences.getFloat(getString(R.string.sugar_used_weight), 0);

        snack1 = getFoodByName(mSharedPreferences.getString(getString(R.string.snack1_name), ""));
        snack2 = getFoodByName(mSharedPreferences.getString(getString(R.string.snack2_name), ""));
        breakfast1 = getFoodByName(mSharedPreferences.getString(getString(R.string.breakfast1_name), ""));
        breakfast2 = getFoodByName(mSharedPreferences.getString(getString(R.string.breakfast2_name), ""));
        lunch1 = getFoodByName(mSharedPreferences.getString(getString(R.string.lunch1_name), ""));
        lunch2 = getFoodByName(mSharedPreferences.getString(getString(R.string.lunch2_name), ""));
        dinner1 = getFoodByName(mSharedPreferences.getString(getString(R.string.dinner1_name), ""));
        dinner2 = getFoodByName(mSharedPreferences.getString(getString(R.string.dinner2_name), ""));

        snack1_g = mSharedPreferences.getFloat(getString(R.string.snack1_weight), 0);
        snack2_g = mSharedPreferences.getFloat(getString(R.string.snack2_weight), 0);
        breakfast1_g = mSharedPreferences.getFloat(getString(R.string.breakfast1_weight), 0);
        breakfast2_g = mSharedPreferences.getFloat(getString(R.string.breakfast2_weight), 0);
        lunch1_g = mSharedPreferences.getFloat(getString(R.string.lunch1_weight), 0);
        lunch2_g = mSharedPreferences.getFloat(getString(R.string.lunch2_weight), 0);
        dinner1_g = mSharedPreferences.getFloat(getString(R.string.dinner1_weight), 0);
        dinner2_g = mSharedPreferences.getFloat(getString(R.string.dinner2_weight), 0);
    }

    public float getMainMenuFats() {
        float menuFats = 0;
        menuFats += (breakfast1.getMacros().getFat() * breakfast1_g + breakfast2.getMacros().getFat() * breakfast2_g) / 100;
        menuFats += (lunch1.getMacros().getFat() * lunch1_g + lunch2.getMacros().getFat() * lunch2_g) / 100;
        menuFats += (dinner1.getMacros().getFat() * dinner1_g + dinner2.getMacros().getFat() * dinner2_g) / 100;
        return menuFats;
    }

    public float getMainMenuProteins() {
        float menuProteins = 0;
        menuProteins += (breakfast1.getMacros().getProteins() * breakfast1_g + breakfast2.getMacros().getProteins() * breakfast2_g) / 100;
        menuProteins += (lunch1.getMacros().getProteins() * lunch1_g + lunch2.getMacros().getProteins() * lunch2_g) / 100;
        menuProteins += (dinner1.getMacros().getProteins() * dinner1_g + dinner2.getMacros().getProteins() * dinner2_g) / 100;
        return menuProteins;
    }

    public float getMainMenuCarbs() {
        float menuCarbs = 0;
        menuCarbs += (breakfast1.getMacros().getCarbohydrate().getCarbs() * breakfast1_g + breakfast2.getMacros().getCarbohydrate().getCarbs() * breakfast2_g) / 100;
        menuCarbs += (lunch1.getMacros().getCarbohydrate().getCarbs() * lunch1_g + lunch2.getMacros().getCarbohydrate().getCarbs() * lunch2_g) / 100;
        menuCarbs += (dinner1.getMacros().getCarbohydrate().getCarbs() * dinner1_g + dinner2.getMacros().getCarbohydrate().getCarbs() * dinner2_g) / 100;
        return menuCarbs;
    }

    public Food getFoodByName(String name) {
        for (Food food : mFoods) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
    }

    public boolean foodInMenu(Food food) {
        if (foodsInMenu.size() > 0) {
            for (int i = 0; i < foodsInMenu.size(); i++) {
                if (food.getName().equals(foodsInMenu.get(i).getName())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

}
