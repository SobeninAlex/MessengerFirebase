package com.example.messengerfirebase.model;

public class User {

    private String id;
    private String name;
    private String laseNeme;
    private int age;
    private boolean isOnline;

    public User(String id, String name, String laseNeme, int age, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.laseNeme = laseNeme;
        this.age = age;
        this.isOnline = isOnline;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLaseNeme() {
        return laseNeme;
    }

    public int getAge() {
        return age;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
