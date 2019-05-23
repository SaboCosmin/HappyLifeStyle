package com.hls.happylifestyle;

public class Profile {
    private String name;
    private String gender;
    private String purpose;
    private int age;
    private int activity_level;
    private float height;
    private float weight;

    public Profile(String name,String gender, int age, int activity_level, float height, float weight, String purpose) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.activity_level = activity_level;
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

    public int getActivity_level() {
        return activity_level;
    }

    public void setActivity_level(int activity_level) {
        this.activity_level = activity_level;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
