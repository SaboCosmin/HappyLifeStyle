package com.hls.happylifestyle.Classes;

public class Carbohydrate {
    private int carbs, sugar, fiber;

    public Carbohydrate() {
        this.carbs = 0;
        this.sugar = 0;
        this.fiber = 0;

    }

    public Carbohydrate(int carbs, int sugar, int fiber) {
        this.carbs = carbs;
        this.sugar = sugar;
        this.fiber = fiber;
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
}
