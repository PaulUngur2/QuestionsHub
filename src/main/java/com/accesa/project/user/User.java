package com.accesa.project.user;

import java.util.List;

public class User {
    private final boolean isAdmin;
    private final int id;
    private final String name;
    private final String password;
    private int tokens;
    private List<String> badges;

    public User(String name, String password, int id, int tokens, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.tokens = tokens;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void addTokens(int tokens) {
        this.tokens += tokens;
    }

    public String getName() {
        return name;
    }

    public int getTokens() {
        return tokens;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getId() {
        return id;
    }
}