package com.kangmin.jdbc.model;

public class Account {

    private int id;
    private String username;
    private String password;
    private String displayName;

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String s) {
        this.password = s;
    }

    public void setUsername(String s) {
        this.username = s;
    }

    public void setDisplayName(String s) {
        this.displayName = s;
    }

    @Override
    public String toString() {
        return "Account{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", displayName='" + displayName + '\'' +
            '}';
    }
}
