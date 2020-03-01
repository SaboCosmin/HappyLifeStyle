package com.hls.happylifestyle.Classes;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.io.Console;

public class Food {
    private  String name, description, type, goesWith;
    private float calories;
    private float price;
    private float timeToPrepare;
    private float minWeight;
    private float maxWeight;
    private Macronutrient macros;

    public Food(){
        this.name = "DefaultFoodName";
        this.description = "default description";
        this.type = "defaultType";
        this.goesWith = "defaultGoesWith";
        this.calories = 0;
        this.price = 0;
        this.timeToPrepare = 0;
        this.minWeight = 0;
        this.maxWeight = 1;
        this.macros = new Macronutrient();
    }

    public Food(String name, String description, String type, String goesWith, float calories, float price, float timeToPrepare, float minWeight, float maxWeight, Macronutrient macros) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.goesWith = goesWith;
        this.calories = calories;
        this.price = price;
        this.timeToPrepare = timeToPrepare;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.macros = macros;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoesWith() {
        return goesWith;
    }

    public void setGoesWith(String goesWith) {
        this.goesWith = goesWith;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(float timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }

    public float getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(float minWeight) {
        this.minWeight = minWeight;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Macronutrient getMacros() {
        return macros;
    }

    public void setMacros(Macronutrient macros) {
        this.macros = macros;
    }

    public void getSnapshot(DataSnapshot child){
        this.name = child.child("name").getValue(String.class);
        this.description = child.child("description").getValue(String.class);
        this.type = child.child("type").getValue(String.class);
        this.goesWith = child.child("goesWith").getValue(String.class);
        this.calories = Float.parseFloat(child.child("calories").getValue(String.class));
        this.price = Float.parseFloat(child.child("price").getValue(Long.class).toString());
        this.timeToPrepare = Float.parseFloat(child.child("timeToPrepare").getValue(Long.class).toString());
        this.minWeight = Float.parseFloat(child.child("minWeight").getValue(Long.class).toString());
        this.maxWeight = Float.parseFloat(child.child("maxWeight").getValue(Long.class).toString());
        this.macros.getSnapshot(child.child("macros"));
    }
}
