package com.example.devicemanagement.Entities;

public class Room {
    private String id;
    private String name;
    private int floor;

    public Room(){}

    public Room(String id, String name, int floor) {
        this.id = id;
        this.name = name;
        this.floor = floor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
