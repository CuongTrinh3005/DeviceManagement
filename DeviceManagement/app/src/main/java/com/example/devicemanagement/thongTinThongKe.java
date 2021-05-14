package com.example.devicemanagement;

public class thongTinThongKe {
    private String id;
    private String NameTB;
    private int SL;
    private int InYear;

    public thongTinThongKe(){}

    public thongTinThongKe(String id, String nameTB, int SL, int inYear) {
        this.id = id;
        NameTB = nameTB;
        this.SL = SL;
        InYear = inYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameTB() {
        return NameTB;
    }

    public void setNameTB(String nameTB) {
        NameTB = nameTB;
    }

    public int getSL() {
        return SL;
    }

    public void setSL(int SL) {
        this.SL = SL;
    }

    public int getInYear() {
        return InYear;
    }

    public void setInYear(int inYear) {
        InYear = inYear;
    }
}
