package com.app.server.account;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    private String username;
    private String password;
    private String email;
    private Date dateCreated;
    private Date lastLogin;

    private boolean isOnline;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateCreated = new Date(System.currentTimeMillis());
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String result = gson.toJson(this);
        return result + "\n";
    }
}
