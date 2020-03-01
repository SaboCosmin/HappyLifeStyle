package com.hls.happylifestyle.Classes;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String name, gender, purpose;
    private int age, activityLevel, height, weight;

    public UserProfile(String name, String gender, int age, int activityLevel, int height, int weight, String purpose) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.activityLevel = activityLevel;
        this.height = height;
        this.weight = weight;
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
