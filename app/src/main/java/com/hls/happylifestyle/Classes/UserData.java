package com.hls.happylifestyle.Classes;

public class UserData {
    private String email, role;

    public UserData() {
        this.email = "default@email.com";
        this.role = "user";
    }

    public UserData(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
