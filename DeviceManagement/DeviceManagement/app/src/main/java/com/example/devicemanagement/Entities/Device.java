package com.example.devicemanagement.Entities;

import java.sql.Blob;

public class Device {
    private String id;
    private String name;
    private String typeId;
    private String origin;
    private Blob image;
    private int quantity;
    private String state;

    public Device(){}

    public Device(String id, String name, String typeId, String origin, Blob image, int quantity, String state) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.origin = origin;
        this.image = image;
        this.quantity = quantity;
        this.state = state;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
