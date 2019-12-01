package com.hls.happylifestyle;

public class MenuElement {
    private Food foods[];
    private int weightOfFoods[];

    public MenuElement(Food[] foods, int[] weightOfFoods) {
        this.foods = foods;
        this.weightOfFoods = weightOfFoods;
    }

    public Food[] getFoods() {
        return foods;
    }

    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    public int[] getWeightOfFoods() {
        return weightOfFoods;
    }

    public void setWeightOfFoods(int[] weightOfFoods) {
        this.weightOfFoods = weightOfFoods;
    }

    public float totalProteins(){
        float total = 0;
        for(int i = 0; i < foods.length; i++){
            total += foods[i].getProteins() * weightOfFoods[i]/100;
        }
        return total;
    }
    public float totalFats(){
        float total = 0;
        for(int i = 0; i < foods.length; i++){
            total += foods[i].getFat() * weightOfFoods[i]/100;
        }
        return total;
    }
    public float totalCarbs(){
        float total = 0;
        for(int i = 0; i < foods.length; i++){
            total += foods[i].getCarbs() * weightOfFoods[i]/100;
        }
        return total;
    }
    public float totalCalories(){
        float total = 0;
        for(int i = 0; i < foods.length; i++){
            total += foods[i].getCalories() * weightOfFoods[i]/100;
        }
        return total;
    }
    public float totalSugar(){
        float total = 0;
        for(int i = 0; i < foods.length; i++){
            total += foods[i].getSugar() * weightOfFoods[i]/100;
        }
        return total;
    }
    public float totalFibers(){
        float total = 0;
        for(int i = 0; i < foods.length; i++){
            total += foods[i].getFiber() * weightOfFoods[i]/100;
        }
        return total;
    }
}
