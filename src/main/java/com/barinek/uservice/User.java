package com.barinek.uservice;

public class User {
    private int id;
    private String name;

    public User() { // for jackson
    }

    public User(String name) { // for rest
        this.name = name;
    }

    public User(int id, String name) { // for jdbc
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
