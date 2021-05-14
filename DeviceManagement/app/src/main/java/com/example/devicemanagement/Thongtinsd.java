package com.example.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Entities.Device;
import com.example.devicemanagement.Entities.TypeOfDevice;

import java.util.List;

public class Thongtinsd extends AppCompatActivity {

    DatabaseHandler handler = new DatabaseHandler(this);
    Spinner sp;
    TextView tenLoai;
    TableLayout tableLayout;

    TableRow tableRow;
    public void setControl(){
        sp = (Spinner) findViewById(R.id.sp_maloai);
        tenLoai = (TextView) findViewById(R.id.tv_tenloai);
        tableLayout = (TableLayout) findViewById(R.id.tb_device);
        tableRow=(TableRow) findViewById(R.id.test_row);
    }

    public void display(String type){
        List<Device> devices = handler.getDevicesByType(type);

        int count = 0;
        for(Device d : devices) {
            count++;
            TableRow tr = new TableRow(this);
            TextView tv_no = new TextView(this);
            TextView tv_id = new TextView(this);
            TextView tv_name= new TextView(this);
            TextView tv_xx= new TextView(this);


            ((TextView)tableRow.findViewById(R.id.test_no)).measure(0,0);
            tv_no.setWidth(((TextView)tableRow.findViewById(R.id.test_no)).getMeasuredWidth());
            tr.addView(tv_no);
            tv_no.setText(String.valueOf(count));
//            tv_no.setWidth(150);
            tv_no.setTextSize(16);
            tv_no.setGravity(Gravity.CENTER);
            tv_no.setPadding(15,15,15,15);

            ((TextView)tableRow.findViewById(R.id.test_device_id)).measure(0,0);
            tv_id.setWidth(((TextView)tableRow.findViewById(R.id.test_device_id)).getMeasuredWidth());
            tr.addView(tv_id);
            tv_id.setTextSize(16);
            tv_id.setGravity(Gravity.CENTER);
            tv_id.setText(d.getId());
            tv_id.setPadding(15,15,15,15);


            ((TextView)tableRow.findViewById(R.id.test_device_name)).measure(0,0);
            tv_name.setWidth(((TextView)tableRow.findViewById(R.id.test_device_name)).getMeasuredWidth());
            tr.addView(tv_name);
            tv_name.setText(d.getName());
            tv_name.setTextSize(16);
            tv_name.setGravity(Gravity.CENTER);
            tv_name.setPadding(15,15,15,15);


            ((TextView)tableRow.findViewById(R.id.test_device_origin)).measure(0,0);
            tv_xx.setWidth(((TextView)tableRow.findViewById(R.id.test_device_origin)).getMeasuredWidth());
            tr.addView(tv_xx);
            tv_xx.setText(d.getOrigin());
//            tv_xx.setWidth(250);
            tv_xx.setTextSize(16);
            tv_xx.setGravity(Gravity.CENTER);
            tv_xx.setPadding(15,15,15,15);

            tableLayout.addView(tr);


        }

        if(count == 0){
            Toast.makeText(this, "Không có thiết bị nào ", Toast.LENGTH_SHORT).show();
        }
    }


    public void setEvent(){
        //get data
        List<String> listid = handler.getAllTypeOfDeviceId();
        ArrayAdapter<String> arrTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listid);
        arrTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrTypes);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            List<TypeOfDevice> list = handler.getAllTypeOfDevice();
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeOfDevice type = list.get(position);
                tenLoai.setText(type.getName());
                //displayTable();
                tableLayout.removeAllViews();
                display(type.getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//        getSupportActionBar().setTitle("Thông tin sử dụng");

        setControl();
        setEvent();
    }
}