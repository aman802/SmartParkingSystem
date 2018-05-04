package com.example.aman.smartparkingsystem;

public class User {
    String name, phone, email,id,LP1,LP2,LP3;

    public User(String name, String phone, String email, String id, String LP1, String LP2, String LP3) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.LP1 = LP1;
        this.LP2 = LP2;
        this.LP3 = LP3;
    }

    public User(String name, String phone, String email, String id, String LP1, String LP2) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.LP1 = LP1;
        this.LP2 = LP2;
        this.LP3 = "-";
    }

    public User(String name, String phone, String email, String id, String LP1) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.LP1 = LP1;
        this.LP2 = "-";
        this.LP3 = "-";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
