package com.example.messengerfirebase.model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String name;
    private String lastNeme;
    private int age;
    private boolean online;

    public User(String id, String name, String laseNeme, int age, boolean online) {
        this.id = id;
        this.name = name;
        this.lastNeme = laseNeme;
        this.age = age;
        this.online = online;
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
        return online;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastNeme='" + lastNeme + '\'' +
                ", age=" + age +
                ", isOnline=" + online +
                '}';
    }
}
