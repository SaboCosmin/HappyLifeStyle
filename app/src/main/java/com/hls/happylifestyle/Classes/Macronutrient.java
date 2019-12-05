package com.hls.happylifestyle.Classes;

public class Macronutrient {
    private int proteins, fat;
    Carbohydrate carbohydrate;

    public  Macronutrient(){
        this.proteins = 0;
        this.fat = 0;
        carbohydrate = new Carbohydrate();
    }

    public Macronutrient(int proteins, int fat, Carbohydrate carbohydrate) {
        this.proteins = proteins;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public Carbohydrate getCarbohydrate() {
        return carbohydrate;
}

    public void setCarbohydrate(Carbohydrate carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

}
