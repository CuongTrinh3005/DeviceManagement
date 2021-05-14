package com.example.devicemanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Entities.Device;
import com.example.devicemanagement.Entities.TypeOfDevice;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DevicesManageActivity extends AppCompatActivity {
    EditText edtId, edtName, edtOrigin, edtQuantity;
    TextView tvUpload;
    Spinner spTypes, spStates;
    ListView deviceListView;
    List<Device> deviceList = new ArrayList();
    DeviceAdapter deviceAdapter;
    public static Device device;
    List<TypeOfDevice> typesList;
    ArrayAdapter<TypeOfDevice> typeAdapter;
    private Button btnAdd, btnUpdate, btnClear, btnLoad;
    public static final int GET_FROM_GALLERY = 100;
    Bitmap bitmap;
    byte[] compressImage = new byte[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_manage);
        getSupportActionBar().setTitle("Device Activity");
        init();
        setControl();
        setEvent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
//            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                compressImage = getBitmapAsByteArray(bitmap);
                Log.d("Compress bytes", String.valueOf(compressImage));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void setControl() {
        tvUpload = findViewById(R.id.tvUpload);
        edtId = findViewById(R.id.edtDeviceId);
        edtName = findViewById(R.id.edtDeviceName);
        edtOrigin = findViewById(R.id.edtOrigin);
        edtQuantity = findViewById(R.id.edtQuantity);
        spTypes = findViewById(R.id.spinnerTypes);
        // Loading spinner data from database
        loadSpinnerTypesData();
        spStates = findViewById(R.id.spinnerStates);
        deviceListView = findViewById(R.id.deviceList);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnClear = findViewById(R.id.btnClear);
        btnLoad = findViewById(R.id.btnLoad);
    }

    private void setEvent() {
        deviceAdapter = new DeviceAdapter(this, deviceList, R.layout.device_list);
        deviceListView.setAdapter(deviceAdapter);
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                device = (Device) deviceList.get(position);
                // Set values for edit text
                edtId.setText(device.getId());
                edtName.setText(device.getName());
                TypeOfDevice type = findTypeById(device.getTypeId());
                setDataForSpinnerType(type);
                edtOrigin.setText(device.getOrigin());
                edtQuantity.setText(String.valueOf(device.getQuantity()));
                setDataForSpinnerState(device.getState());
            }
        });
        // set item for spinner types of device
        spTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeOfDevice type = (TypeOfDevice) parent.getSelectedItem();
//                Toast.makeText(getApplicationContext(), type.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataEntryIsEmpty()){
                    Toast.makeText(getApplication(), "Please enter information", Toast.LENGTH_SHORT).show();
                    return;
                }
                Boolean idExisted = checkIdExist(edtId.getText().toString().trim());
                if (idExisted) {
                    Toast.makeText(getApplication(), "ID's already existed", Toast.LENGTH_SHORT).show();
                } else {
                    String id = edtId.getText().toString().trim();
                    String name = edtName.getText().toString().trim();
                    String origin = edtOrigin.getText().toString().trim();
                    int quantity = Integer.valueOf(edtQuantity.getText().toString());
                    TypeOfDevice type = (TypeOfDevice) spTypes.getSelectedItem();
                    String typeId = type.getId();
                    String state = spStates.getSelectedItem().toString().trim();

                    Device device = null;
                    if(compressImage.length > 0){
                        device = new Device(id, name, typeId, origin, compressImage, quantity, state);
                        // reset image byte array
                        compressImage = new byte[0];
                    }
                    else
                        device = new Device(id, name, typeId, origin, null, quantity, state);
                    DatabaseHandler db = new DatabaseHandler(com.example.devicemanagement.DevicesManageActivity.this);
                    db.addDevice(device);
                    loadData();
                    Toast.makeText(getApplication(), "Add new device successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataEntryIsEmpty()){
                    Toast.makeText(getApplication(), "Please enter information", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseHandler db = new DatabaseHandler(com.example.devicemanagement.DevicesManageActivity.this);

                // Get all current values
                String id = edtId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String origin = edtOrigin.getText().toString().trim();
                int quantity = Integer.valueOf(edtQuantity.getText().toString());
                TypeOfDevice type = (TypeOfDevice) spTypes.getSelectedItem();
                String typeId = type.getId();
                String state = spStates.getSelectedItem().toString().trim();

                Device newDevice = null;
                if(compressImage.length > 0){ // edit image (upload the new one)
                    newDevice = new Device(id, name, typeId, origin, compressImage, quantity, state);
                    // reset image byte array
                    compressImage = new byte[0];
                }
                else{
                    // if no image uploaded, remains the old image
                    // get current device
                    Device currentDevice = db.getDevice(id);
                    newDevice = new Device(id, name, typeId, origin, currentDevice.getImage(), quantity, state);
                }

                int result = db.updateDevice(newDevice);
                if (result > 0) {
                    Toast.makeText(getBaseContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                    loadData();
                } else Toast.makeText(getBaseContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtId.setText("");
                edtName.setText("");
                edtOrigin.setText("");
                edtQuantity.setText("");
            }
        });
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Test view gallery
                Toast.makeText(getApplication(), "Switch to gallery", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
    }

    private void init() {
        DatabaseHandler db = new DatabaseHandler(this);
        deviceList = db.getAllDevice();
    }

    private void loadData() {
        DatabaseHandler db = new DatabaseHandler(this);
        deviceList.clear();
        List<Device> newList = db.getAllDevice();
        deviceList.addAll(newList);
        deviceAdapter.notifyDataSetChanged();
    }

    public void deleteDV(Device device) {
        // Check if device is in borrow list
        DatabaseHandler db = new DatabaseHandler(this);
        boolean existedInBorrow = db.checkIfDeviceInBorrow(device);
        if(existedInBorrow){
            Toast.makeText(getApplicationContext(), "Can not delete item in borrow list", Toast.LENGTH_SHORT).show();
            return;
        }
        db.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(com.example.devicemanagement.DevicesManageActivity.this);

        builder.setMessage("Do you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(device.getState().equals("OK")){
                    Toast.makeText(getApplicationContext(), "Not allow to delete good device", Toast.LENGTH_SHORT).show(); return;
                }
                DatabaseHandler db = new DatabaseHandler(com.example.devicemanagement.DevicesManageActivity.this);
                db.deleteDevice(device);
                loadData();
                Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void loadSpinnerTypesData() {
        DatabaseHandler db = new DatabaseHandler(this);
        typesList = db.getAllTypeOfDevice();
        // Create an adapter for spinner
        typeAdapter = new ArrayAdapter<TypeOfDevice>(this,
                android.R.layout.simple_spinner_item, typesList);
        // Drop down layout style - list view with radio button
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spTypes.setAdapter(typeAdapter);
    }

    private void setDataForSpinnerType(TypeOfDevice type) {
        if (type != null) {
//            int spinnerPosition = typeAdapter.getPosition(type);
//            spTypes.setSelection(spinnerPosition);
            for(int i=0; i<typeAdapter.getCount();i++){
                if(typeAdapter.getItem(i).equals(type)){
                    spTypes.setSelection(i);
                    return;
                }
            }
        }
    }

    private void setDataForSpinnerState(String state) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStates.setAdapter(adapter);
        if (state != null) {
            int spinnerPosition = adapter.getPosition(state);
            spStates.setSelection(spinnerPosition);
        }
    }

    private TypeOfDevice findTypeById(String id) {
        DatabaseHandler db = new DatabaseHandler(this);
        List<TypeOfDevice> typesList = db.getAllTypeOfDevice();
        for (TypeOfDevice type : typesList) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    private Boolean checkIdExist(String id) {
        for (Device device : deviceList) {
            if (device.getId().equalsIgnoreCase(id))
                return true;
        }
        return false;
    }

    private boolean dataEntryIsEmpty(){
        if(edtId.getText().toString().equals("") || edtName.getText().toString().equals("") ||
                edtOrigin.getText().toString().equals("") || edtQuantity.getText().toString().trim().equals("")){
            return true;
        }
        return false;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        }
        catch (NullPointerException e){
            Log.d("Compress status", e.toString());
        }
        return outputStream.toByteArray();

//        int size = bitmap.getRowBytes() * bitmap.getHeight();
//        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
//        bitmap.copyPixelsToBuffer(byteBuffer);
//        byte[] byteArray = byteBuffer.array();
//        return byteArray;
    }

    public static Bitmap restoreImage(byte[] imageBytes){
        BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
    }

//    public Bitmap restoreImage(byte[] imageBytes){
        //Create bitmap with width, height, and 4 bytes color (RGBA)
//        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        ByteBuffer buffer = ByteBuffer.wrap(imageBytes);
//        buffer.rewind();
//        bmp.copyPixelsFromBuffer(buffer);
//        return bmp;
//        Bitmap.Config configBmp = Bitmap.Config.valueOf(bitmap.getConfig().name());
//        Bitmap bitmap_tmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), configBmp);
//        ByteBuffer buffer = ByteBuffer.wrap(imageBytes);
//        bitmap_tmp.copyPixelsFromBuffer(buffer);
//        return bitmap_tmp;
//    }
}