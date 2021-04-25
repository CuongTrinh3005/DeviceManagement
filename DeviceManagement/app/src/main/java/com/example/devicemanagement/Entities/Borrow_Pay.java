package com.example.devicemanagement.Entities;

import java.util.Date;

public class Borrow_Pay {
    private int id;
    private String studentId;
    private String roomId;
    private Date borrowDay;

    public Borrow_Pay(){}

    public Borrow_Pay(int id, String studentId, String roomId, Date borrowDay) {
        this.id = id;
        this.studentId = studentId;
        this.roomId = roomId;
        this.borrowDay = borrowDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(Date borrowDay) {
        this.borrowDay = borrowDay;
    }
}
