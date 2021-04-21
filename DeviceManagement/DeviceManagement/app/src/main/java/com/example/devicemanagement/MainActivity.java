package com.example.devicemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.devicemanagement.Entities.Device;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler handler = new DatabaseHandler(this);

        TextView tvContent = findViewById(R.id.content);
        String info = "";

        // Test list of managers
//        List<Manager> list = handler.getAllManagers();
//        if(list.size() >  0){
//            Manager manager = list.get(0);
//            info = "ID: " + manager.getId();
//            info += "\nPassword: " + manager.getPassword();
//            info += "\nName: " + manager.getName();
//            info += "\nGender: " + manager.isGender();
//            info += "\nBirthday: " + manager.getBirthday();
//        }
//
//        else{
//            info = "NO MANAGER";
//        }

        // Test types of devices
//        List<TypeOfDevice> list = handler.getAllTypeOfDevice();
//        if(list.size() >  0){
//            TypeOfDevice typeOfDevice = list.get(0);
//            info = "ID: " + typeOfDevice.getId();
//            info += "\nName: " + typeOfDevice.getName();
//        }
//        else{
//            info = "NO MANAGER";
//        }
//        tvContent.setText(info);

        // Test list of devices
        List<Device> list = handler.getAllDevice();
        if(list.size() >  0){
            Device device = list.get(0);
            info = "ID: " + device.getId();
            info += "\nName: " + device.getName();
        }
        else{
            info = "NO MANAGER";
        }
        tvContent.setText(info);
    }
}