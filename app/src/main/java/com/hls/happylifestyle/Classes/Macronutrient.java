package com.hls.happylifestyle.Classes;

import com.google.firebase.database.DataSnapshot;

public class Macronutrient {
    private float proteins;
    private float fat;
    Carbohydrate carbohydrate;

    public  Macronutrient() {
        this.proteins = 0;
        this.fat = 0;
        carbohydrate = new Carbohydrate();
    }

    public Macronutrient(float proteins, float fat, Carbohydrate carbohydrate) {
        this.proteins = proteins;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public Carbohydrate getCarbohydrate() {
        return carbohydrate;
}

    public void setCarbohydrate(Carbohydrate carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void getSnapshot(DataSnapshot child) {
        this.proteins = Float.parseFloat(child.child("proteins").getValue(String.class));
        this.fat = Float.parseFloat(child.child("fat").getValue(String.class));
        this.carbohydrate.getSnapshot(child.child("carbohydrate"));
    }
}
