package com.hls.happylifestyle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class SeeMenuActivity extends AppCompatActivity {
    private TextView proteinsCounter, proteinsTotal, carbsCounter, carbsTotal, fatsCounter, fatsTotal;
    private TextView fiberCounter, sugarCounter,caloriesCounter, caloriesTotal;
    private TextView snack1_tv, snack2_tv, breakfast1_tv, breakfast2_tv, lunch1_tv, lunch2_tv, dinner1_tv, dinner2_tv;
    private TextView snack1_g_tv, snack2_g_tv, breakfast1_g_tv, breakfast2_g_tv, lunch1_g_tv, lunch2_g_tv, dinner1_g_tv, dinner2_g_tv;
    private ArrayList<Food> snack, egg, dairy, cereal, fastFood, fish, seafood, fruit;
    private ArrayList<Food> meat, nuts, pasta, rice, salad, soup, desert, vegetable;
    private Food snack1, snack2, breakfast1, breakfast2, lunch1, lunch2, dinner1, dinner2;
    private float snack1_g, snack2_g, breakfast1_g, breakfast2_g, lunch1_g, lunch2_g, dinner1_g, dinner2_g;
    private float proteinsUsed, carbsUsed, fatsUsed, caloriesUsed, fiberUsed, sugarUsed;

    private DatabaseReference mDatabaseReference;
    final ArrayList<Food> mFoods = new ArrayList<>();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_menu);

        if (getIntent().hasExtra("pageNameActivity")){
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("foods");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child : children){
                    Food value = child.getValue(Food.class);
                    mFoods.add(value);
                }
                for (Food food : mFoods){
                    String[] type = food.getType().split(",");
                    for(int i = 0; i< type.length; i++){
                        putFood(food, type[i]);
                    }
                }
                generateMenu();
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

    public void initializeViews(){
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
    }

    public void populateMenu(){
        breakfast1_tv.setText(breakfast1.getName());
        breakfast2_tv.setText(breakfast2.getName());
        breakfast1_g_tv.setText(breakfast1_g + "g");
        breakfast2_g_tv.setText(breakfast2_g + "g");

        lunch1_tv.setText(lunch1.getName());
        lunch2_tv.setText(lunch2.getName());
        lunch1_g_tv.setText(lunch1_g + "g");
        lunch2_g_tv.setText(lunch2_g + "g");

        dinner1_tv.setText(dinner1.getName());
        dinner2_tv.setText(dinner2.getName());
        dinner1_g_tv.setText(dinner1_g + "g");
        dinner2_g_tv.setText(dinner2_g + "g");
    }

    public void populateViews(){
        caloriesTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_calories_total), 1f)) + "g total");
        proteinsTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_proteins_total), 1f)) + "g total");
        fatsTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_fats_total), 1f)) + "g total");
        carbsTotal.setText(Math.round(mSharedPreferences.getFloat(getString(R.string.user_key_carbs_total), 1f)) + "g total");


        caloriesCounter.setText("Calories consumed: " + caloriesUsed + "g");
        proteinsCounter.setText("Proteins consumed: " + proteinsUsed + "g");
        fatsCounter.setText("Fats consumed: " + fatsUsed + "g");
        carbsCounter.setText("Carbs consumed: " + carbsUsed + "g");
        fiberCounter.setText(fiberUsed + "g");
        sugarCounter.setText(sugarUsed + "g");
    }

    public void generateMenu(){
        float proteinsTotal, fatsTotal, carbsTotal;
        proteinsTotal = mSharedPreferences.getFloat(getString(R.string.user_key_proteins_total), 1f);
        fatsTotal = mSharedPreferences.getFloat(getString(R.string.user_key_fats_total), 1f);
        carbsTotal = mSharedPreferences.getFloat(getString(R.string.user_key_carbs_total), 1f);
        generateDinner(Math.round(proteinsTotal * 0.3f), Math.round(carbsTotal * 0.3f), Math.round(fatsTotal * 0.3f));
        generateBreakfast(Math.round(proteinsTotal * 0.3f), Math.round(carbsTotal * 0.3f), Math.round(fatsTotal * 0.3f));
        generateLaunch(Math.round(proteinsTotal * 0.4f), Math.round(carbsTotal * 0.4f), Math.round(fatsTotal * 0.4f));
        float x = (breakfast1.getProteins()* breakfast1_g + lunch1.getProteins()* lunch1_g + dinner1.getProteins()* dinner1_g + breakfast2.getProteins()* breakfast2_g + lunch2.getProteins()* lunch2_g + dinner2.getProteins()* dinner2_g)/100;
        float y = (breakfast1.getCarbs() * breakfast1_g + lunch1.getCarbs() * lunch1_g + dinner1.getCarbs() * dinner1_g + breakfast2.getCarbs()* breakfast2_g + lunch2.getCarbs()* lunch2_g + dinner2.getCarbs()* dinner2_g)/100;
        float z = (breakfast1.getFat() * breakfast1_g + lunch1.getFat() * lunch1_g + dinner1.getFat() * dinner1_g + breakfast2.getFat()* breakfast2_g + lunch2.getFat()* lunch2_g + dinner2.getFat()* dinner2_g)/100;
        Log.d("total", "total proteins: " + x);
        Log.d("total", "total carbs: " + y);
        Log.d("total", "total fats: " + z);
        Log.d("total", "total calories: " +  (x*4 + y*4 + z*9));
        //generateSnack();
    }

    private void generateSnack() {

    }

    private void initializeFood(){
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


    }

    private void generateDinner(int proteins, int carbs, int fats) {
        String[] is = {"salad", "fish", "meat", "pasta", "rice", "desert", "vegetable"};
        dinner1 = getRandomFood(getRandomList(is));
        String[] goesWith = dinner1.getGoesWith().split(",");
        dinner2 = getRandomFood(getRandomList(goesWith));
        int[] weight = setValues(dinner1, dinner2, proteins, carbs, fats);
        dinner1_g = weight[0];
        dinner2_g = weight[1];
    }

    private void generateBreakfast(int proteins, int carbs, int fats) {
        String[] is = {"egg", "dairy", "cereal", "nuts", "salad"};
        breakfast1 = getRandomFood(getRandomList(is));
        String[] goesWith = breakfast1.getGoesWith().split(",");
        breakfast2 = getRandomFood(getRandomList(goesWith));
        int[] weight = setValues(breakfast1, breakfast2, proteins, carbs, fats);
        breakfast1_g = weight[0];
        breakfast2_g = weight[1];
    }

    private void generateLaunch(int proteins, int carbs, int fats) {
        String[] is = {"fast food", "fish", "meat", "pasta", "rice", "salad", "desert"};
        lunch1 = getRandomFood(getRandomList(is));
        String[] goesWith = lunch1.getGoesWith().split(",");
        lunch2 = getRandomFood(getRandomList(goesWith));
        int[] weight = setValues(lunch1, lunch2, proteins, carbs, fats);
        lunch1_g = weight[0];
        lunch2_g = weight[1];
    }

    public void eatButton(View v){
        String whatEat = v.getTag().toString();
        switch (whatEat){
            case ("breakfast"):
                proteinsUsed = proteinsUsed + (breakfast1.getProteins() * breakfast1_g + breakfast2.getProteins() * breakfast2_g) / 100;
                carbsUsed = carbsUsed + (breakfast1.getCarbs() * breakfast1_g + breakfast2.getCarbs() * breakfast2_g) / 100;
                fatsUsed = fatsUsed + (breakfast1.getFat() * breakfast1_g + breakfast2.getFat() * breakfast2_g) / 100;
                break;
            case ("lunch"):
                proteinsUsed = proteinsUsed + (lunch1.getProteins() * lunch1_g + lunch2.getProteins() * lunch2_g) / 100;
                carbsUsed = carbsUsed + (lunch1.getCarbs() * lunch1_g + lunch2.getCarbs() * lunch2_g) / 100;
                fatsUsed = fatsUsed + (lunch1.getFat() * lunch1_g + lunch2.getFat() * lunch2_g) / 100;
                break;
            case ("dinner"):
                proteinsUsed = proteinsUsed + (dinner1.getProteins() * dinner1_g + dinner2.getProteins() * dinner2_g) / 100;
                carbsUsed = carbsUsed + (dinner1.getCarbs() * dinner1_g + dinner2.getCarbs() * dinner2_g) / 100;
                fatsUsed = fatsUsed + (dinner1.getFat() * dinner1_g + dinner2.getFat() * dinner2_g) / 100;
                break;
            default:
                break;
        }
        caloriesUsed = (proteinsUsed * 4 + carbsUsed * 4 + fatsUsed * 9);
        populateViews();
    }

    public void putFood(Food food, String type){
        switch (type){
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
            case ("fast food"):
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

    public void  replaceButton(View v){
        String whatEat = v.getTag().toString();
        Log.d("test" , whatEat + " replace");
    }

    public ArrayList<Food> getRandomList(String[] goesWith){
        Random random = new Random();
        int randNum;
        Log.d("number", "number for goesWith is: " + goesWith.length);
        if (goesWith.length == 1){
            randNum = 0;
        }else{
            randNum  = random.nextInt(goesWith.length - 1);
        }
        if (goesWith[randNum].equals("snack")){
            return snack;
        }
        if (goesWith[randNum].equals("egg")){
            return egg;
        }
        if (goesWith[randNum].equals("dairy")){
            return dairy;
        }
        if (goesWith[randNum].equals("cereal")){
            return cereal;
        }
        if (goesWith[randNum].equals("fast food")){
            return fastFood;
        }
        if (goesWith[randNum].equals("seafood")){
            return seafood;
        }
        if (goesWith[randNum].equals("fish")){
            return fish;
        }
        if (goesWith[randNum].equals("fruit")){
            return fruit;
        }
        if (goesWith[randNum].equals("meat")){
            return meat;
        }
        if (goesWith[randNum].equals("nuts")){
            return nuts;
        }
        if (goesWith[randNum].equals("rice")){
            return rice;
        }
        if (goesWith[randNum].equals("pasta")){
            return pasta;
        }
        if (goesWith[randNum].equals("salad")){
            return salad;
        }
        if (goesWith[randNum].equals("soup")){
            return soup;
        }
        if (goesWith[randNum].equals("desert")){
            return desert;
        }
        if (goesWith[randNum].equals("vegetable")){
            return vegetable;
        }else {
            return meat;
        }
    }

    public Food getRandomFood(ArrayList<Food> foods){
        Random random = new Random();
        int randNum;
        Log.d("number", "number for food is: " + foods.size());
        if(foods.size() == 1){
            randNum = 0;
        }else{
            randNum  = random.nextInt(foods.size() - 1);
        }
        return foods.get(randNum);
    }

    public int[] setValues(Food f1, Food f2, int proteins, int carbs, int fats){
        int weightF1,weightF2;
        weightF1 = Math.round((f1.getMinWeight() + f1.getMaxWeigh()) / 2);
        weightF2 = Math.round((f2.getMinWeight() + f2.getMaxWeigh()) / 2);

        for(int i = 0; i < 10; i++){
            if(f1.getFat() != 0 || f2.getFat() != 0){
                if(f1.getFat() > f2.getFat()){
                    while(((f1.getFat() * weightF1) / 100 + (f2.getFat() * weightF2) / 100) < fats && weightF1 < f1.getMaxWeigh() && weightF2 < f2.getMaxWeigh()){
                        weightF1 += 30;
                        weightF2 += 10;
                    }
                    while(((f1.getFat() * weightF1) / 100 + (f2.getFat() * weightF2) / 100) > fats && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()){
                        weightF1 -= 10;
                        weightF2 -= 5;
                    }
                }else{
                    while(((f1.getFat() * weightF1) / 100 + (f2.getFat() * weightF2) / 100) < fats && weightF1 < f1.getMaxWeigh() && weightF2 < f2.getMaxWeigh()){
                        weightF2 += 30;
                        weightF1 += 10;
                    }
                    while(((f1.getFat() * weightF1) / 100 + (f2.getFat() * weightF2) / 100 )> fats && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()){
                        weightF2 -= 10;
                        weightF1 -= 5;
                    }
                }
            }

            if(f1.getProteins() != 0 || f2.getProteins() != 0) {
                if (f1.getProteins() > f2.getProteins()) {
                    while (((f1.getProteins() * weightF1) / 100 + (f2.getProteins() * weightF2) / 100) < proteins && weightF1 < f1.getMaxWeigh() && weightF2 < f2.getMaxWeigh()) {
                        weightF1 += 30;
                        weightF2 += 10;
                    }
                    while (((f1.getProteins() * weightF1) / 100 + (f2.getProteins() * weightF2) / 100) > proteins && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF1 -= 10;
                        weightF2 -= 5;
                    }
                } else {
                    while (((f1.getProteins() * weightF1) / 100 + (f2.getProteins() * weightF2) / 100) < proteins && weightF1 < f1.getMaxWeigh() && weightF2 < f2.getMaxWeigh()) {
                        weightF2 += 30;
                        weightF1 += 10;
                    }
                    while (((f1.getProteins() * weightF1) / 100 + (f2.getProteins() * weightF2) / 100) > proteins && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()) {
                        weightF2 -= 10;
                        weightF1 -= 5;
                    }
                }
            }

            if(f1.getCarbs() != 0 || f2.getCarbs() != 0) {
                if(f1.getCarbs() > f2.getCarbs()){
                    while(((f1.getCarbs() * weightF1) / 100 + (f2.getCarbs() * weightF2) / 100 )< carbs && weightF1 < f1.getMaxWeigh() && weightF2 < f2.getMaxWeigh()){
                        weightF1 += 30;
                        weightF2 += 10;
                    }
                    while(((f1.getCarbs() * weightF1) / 100 + (f2.getCarbs() * weightF2) / 100 )> carbs && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()){
                        weightF1 -= 10;
                        weightF2 -= 5;
                    }
                }else{
                    while(((f1.getCarbs() * weightF1) / 100 + (f2.getCarbs() * weightF2) / 100 )< carbs && weightF1 < f1.getMaxWeigh() && weightF2 < f2.getMaxWeigh()){
                        weightF2 += 30;
                        weightF1 += 10;
                    }
                    while(((f1.getCarbs() * weightF1) / 100 + (f2.getCarbs() * weightF2) / 100 )> carbs && weightF1 > f1.getMinWeight() && weightF2 > f2.getMinWeight()){
                        weightF2 -= 10;
                        weightF1 -= 5;
                    }
                }
            }
        }

        int[] result = {weightF1, weightF2};
        return result;
    }

}
