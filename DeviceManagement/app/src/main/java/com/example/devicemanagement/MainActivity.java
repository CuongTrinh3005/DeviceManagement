package com.example.devicemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.sql.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler handler = new DatabaseHandler(this);
        Manager manager = new Manager("MN01", "1234", "Cuong", true,
                Date.valueOf("1999-03-04"));
        handler.saveManager(manager);
    }
}