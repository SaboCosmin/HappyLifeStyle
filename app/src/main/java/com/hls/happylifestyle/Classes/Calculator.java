package com.hls.happylifestyle.Classes;

public class Calculator{

    private float calories;
    private Macronutrient macronutrient;

    public Calculator() {
        this.calories = 0;

    }

    public Calculator(int calories, Macronutrient macronutrient) {
        this.calories = calories;
        this.macronutrient = macronutrient;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public Macronutrient getMacronutrient() {
        return macronutrient;
    }

    public void setMacronutrient(Macronutrient macronutrient) {
        this.macronutrient = macronutrient;
    }
}
