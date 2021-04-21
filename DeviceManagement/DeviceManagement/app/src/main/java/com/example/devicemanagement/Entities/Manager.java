package com.example.devicemanagement.Entities;

import java.sql.Date;

public class Manager {
    private String id;
    private String password;
    private String name;
    private boolean gender;
    private Date birthday;

    public Manager(){}

    public Manager(String id, String password, String name, boolean gender, Date birthday) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday.toString();
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
