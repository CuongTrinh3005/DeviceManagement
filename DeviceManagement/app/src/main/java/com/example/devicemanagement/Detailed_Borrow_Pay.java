package com.example.devicemanagement;

public class Detailed_Borrow_Pay {
    private int id;
    private String deviceId;
    private int numBorrow;
    private int numPay;

    public Detailed_Borrow_Pay(int id, String deviceId, int numBorrow, int numPay) {
        this.id = id;
        this.deviceId = deviceId;
        this.numBorrow = numBorrow;
        this.numPay = numPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getNumBorrow() {
        return numBorrow;
    }

    public void setNumBorrow(int numBorrow) {
        this.numBorrow = numBorrow;
    }

    public int getNumPay() {
        return numPay;
    }

    public void setNumPay(int numPay) {
        this.numPay = numPay;
    }
}
