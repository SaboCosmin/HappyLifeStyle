package com.hls.happylifestyle.Classes;

public class Food {
    private  String name, description, type, goesWith;
    private int calories, price, timeToPrepare, minWeight, maxWeigh;
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
        this.maxWeigh = 1;
        this.macros = new Macronutrient();
    }

    public Food(String name, String description, String type, String goesWith, int calories, int price, int timeToPrepare, int minWeight, int maxWeigh, Macronutrient macros) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.goesWith = goesWith;
        this.calories = calories;
        this.price = price;
        this.timeToPrepare = timeToPrepare;
        this.minWeight = minWeight;
        this.maxWeigh = maxWeigh;
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

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(int timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(int minWeight) {
        this.minWeight = minWeight;
    }

    public int getMaxWeigh() {
        return maxWeigh;
    }

    public void setMaxWeigh(int maxWeigh) {
        this.maxWeigh = maxWeigh;
    }

    public Macronutrient getMacros() {
        return macros;
    }

    public void setMacros(Macronutrient macros) {
        this.macros = macros;
    }
}
