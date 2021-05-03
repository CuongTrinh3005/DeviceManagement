package com.example.devicemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuBorrowPay extends AppCompatActivity {
    Button btnBorrow,btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_borrow_pay);
        setControl();
        setEvent();
    }

    private void setControl() {
        btnBorrow = findViewById(R.id.btnBorrow);
        btnPay = findViewById(R.id.btnPay);
    }

    private void setEvent(){
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuBorrowPay.this, DevicePaysActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuBorrowPay.this, AddBorrowActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}