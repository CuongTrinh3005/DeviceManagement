package com.example.devicemanagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Chitietsd extends AppCompatActivity {
    TextView tv;

    public void setControl(){
        tv = (TextView) findViewById(R.id.tv_typeID);
    }

    public void setEvent(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsd);
        getSupportActionBar().setTitle("Chi tiết sử dụng");
    }
}