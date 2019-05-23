package com.hls.happylifestyle;

public class Food {
    private  String name;
    private String description;
    private int calories;
    private int proteins;
    private int carbs;
    private int sugar;
    private int fiber;
    private int fat;
    private int price;
    private int timeToPrepare;

    public Food(){

    }

    public Food(String name, String description, int calories, int proteins, int carbs, int sugar, int fiber, int fat, int price, int timeToPrepare) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.sugar = sugar;
        this.fiber = fiber;
        this.fat = fat;
        this.price = price;
        this.timeToPrepare = timeToPrepare;
    }

    public Food(Food food) {
        this.name = food.getName();
        this.description = food.getDescription();
        this.calories = food.getCalories();
        this.proteins = food.getProteins();
        this.carbs = food.getCarbs();
        this.sugar = food.getSugar();
        this.fiber = food.getFiber();
        this.fat = food.getFat();
        this.price = food.getPrice();
        this.timeToPrepare = food.getTimeToPrepare();
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

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getFiber() {
        return fiber;
    }

    public void setFiber(int fiber) {
        this.fiber = fiber;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
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
}
