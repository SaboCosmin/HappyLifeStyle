package com.hls.happylifestyle.Classes;

import com.google.firebase.database.DataSnapshot;

public class Carbohydrate {
    private float carbs, sugar, fiber;

    public Carbohydrate() {
        this.carbs = 0;
        this.sugar = 0;
        this.fiber = 0;

    }

    public Carbohydrate(float carbs, float sugar, float fiber) {
        this.carbs = carbs;
        this.sugar = sugar;
        this.fiber = fiber;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public void getSnapshot(DataSnapshot child){
        this.carbs = Float.parseFloat(child.child("carbs").getValue(String.class));
        this.fiber = Float.parseFloat(child.child("fiber").getValue(String.class));
        this.sugar = Float.parseFloat(child.child("sugar").getValue(String.class));
    }
}
