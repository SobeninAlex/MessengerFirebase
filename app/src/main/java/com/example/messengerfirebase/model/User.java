package com.example.messengerfirebase.model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String name;
    private String lastNeme;
    private int age;
    private boolean isOnline;

    public User(String id, String name, String laseNeme, int age, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.lastNeme = laseNeme;
        this.age = age;
        this.isOnline = isOnline;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastNeme() {
        return lastNeme;
    }

    public int getAge() {
        return age;
    }

    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastNeme='" + lastNeme + '\'' +
                ", age=" + age +
                ", isOnline=" + isOnline +
                '}';
    }
}
