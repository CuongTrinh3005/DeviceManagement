package com.example.devicemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.devicemanagement.Entities.Device;

public class showImageInFullScreen extends AppCompatActivity {
    ImageView imageView;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_in_full_screen);

        setControl();

        Intent intent= getIntent();
        Bundle b= intent.getExtras();
        String id = b.getString("deviceId");

        DatabaseHandler db = new DatabaseHandler(this);
        Device device = db.getDevice(id);
        byte[] imageBytes = device.getImage();
        if(imageBytes != null){
            if(imageBytes.length > 0){
                Bitmap decodedImage = DevicesManageActivity.restoreImage(imageBytes);
                if(decodedImage != null)
                    imageView.setImageBitmap(decodedImage);
            }
        }

        setEvent();
    }

    private void setControl(){
        imageView = findViewById(R.id.imageView);
        btnOk = findViewById(R.id.btnOK);
    }

    private void setEvent(){
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}