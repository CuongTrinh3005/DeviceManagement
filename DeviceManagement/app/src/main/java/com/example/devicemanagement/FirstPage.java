package com.example.devicemanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPage extends AppCompatActivity {
    Button btnExit, btnProcessData, btnBorrowPay, btnStatistic, btnLookup;
    TextView name, id;
    ImageView imUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        getSupportActionBar().hide();
        setControl();

        Intent intent= getIntent();
        Bundle b= intent.getExtras();

        name.setText(b.getStringArray("account")[0]);
        id.setText(b.getStringArray("account")[1]);

        setEvent();
    }

    private void setControl(){
        name=findViewById(R.id.tVUserName);
        id=findViewById(R.id.tVID);
        btnExit = findViewById(R.id.btnExit);
        btnProcessData = findViewById(R.id.btnProcessData);
        btnStatistic = findViewById(R.id.btnStatistic);
        btnBorrowPay = findViewById(R.id.btnBorrowPay);
        btnLookup = findViewById(R.id.btnLookup);
        imUser = findViewById(R.id.imgUser);
    }

    private void setEvent(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                finish();
            }
        });
        btnProcessData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FirstPage.this, DevicesManageActivity.class);
                startActivity(intent);
            }
        });
        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FirstPage.this, Thongtinsd.class);
                startActivity(intent);
            }
        });
        btnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FirstPage.this, StatisticByYearActivity.class);
                startActivity(intent);
            }
        });
        btnBorrowPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, MenuBorrowPay.class);
                startActivity(intent);
                finish();
            }
        });
        imUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, UserLayout.class);
                Bundle b = new Bundle();
                b.putString("accountId", id.getText().toString().trim());

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
