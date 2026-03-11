package com.driverondemand.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role; // CUSTOMER, DRIVER, ADMIN
    private String driverStatus;

    // 1️⃣ No-argument constructor
    public User() {
    }

    // 2️⃣ Parameterized constructor
    public User(String name, String email, String phone, String password, String role,String driverStatus) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.driverStatus = driverStatus;
    }

    // 3️⃣ Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    // ❗ password setter still allowed (we hash later)
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }
}
