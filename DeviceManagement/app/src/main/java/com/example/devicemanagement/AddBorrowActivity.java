package com.example.devicemanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Entities.Borrow_Pay;
import com.example.devicemanagement.Entities.Detailed_Borrow_Pay;
import com.example.devicemanagement.Entities.Device;
import com.example.devicemanagement.Entities.Room;
import com.example.devicemanagement.Entities.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddBorrowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_borrow);


        DatabaseHandler handler = new DatabaseHandler(this);

        Button addDetailButton=this.findViewById(R.id.add_borrow_add_detail_borrow_button);
        addDetailButton.setEnabled(false);

        /////Student
        List<String> ls=new ArrayList<String>();
        for(Student student: handler.getAllStudent()){
            ls.add(student.getId()+":"+student.getName());
        }
        Spinner studentSelector=findViewById(R.id.add_borrow_select_student_spinner);
        ArrayAdapter<String> studentListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ls);
        studentSelector.setAdapter(studentListAdapter);

        //////

        //////////////////
        List<String> ls1=new ArrayList<String>();
        for(Room room: handler.getAllRoom()){
            ls1.add(room.getId()+":"+room.getName());
        }
        Spinner roomSelector=findViewById(R.id.add_borrow_select_room_spinner);
        ArrayAdapter<String> roomListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ls1);
        roomSelector.setAdapter(roomListAdapter);

        //////////////////////
        List<String> ls2=new ArrayList<String>();
        for(Device device: handler.getAllDevice()){
            ls2.add(device.getId()+":"+device.getName());
        }
        Spinner deviceSelector=findViewById(R.id.add_borrow_select_device_spinner);
        ArrayAdapter<String> deviceListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ls2);
        deviceSelector.setAdapter(deviceListAdapter);
        //////////////////////
        //////////////////////
        TextView availableQuantity=findViewById(R.id.add_borrow_available);
        deviceSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
//                Log.d("error",deviceSelector.getSelectedItem().toString());
                availableQuantity.setText(handler.getAvailableDeviceQuantity(deviceSelector.getSelectedItem().toString().split(":")[0])+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /////

        //Date picker

        final Calendar myCalendar = Calendar.getInstance();
        TextView edittext= ( TextView) findViewById(R.id.add_borrow_date_picker);
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        edittext.setText(sdf.format(new Date()));

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                edittext.setText(sdf.format(myCalendar.getTime()));


            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddBorrowActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Button
        Button button=findViewById(R.id.add_borrow_add_borrow_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Borrow_Pay> ls=handler.getAllBorrowPayParse();

                    Borrow_Pay bp=new Borrow_Pay();
                    bp.setRoomId(roomSelector.getSelectedItem().toString().split(":")[0]);
                    bp.setStudentId(studentSelector.getSelectedItem().toString().split(":")[0]);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    bp.setBorrowDay(format.parse(edittext.getText().toString()));
                    bp.setId(ls.size()>0?ls.get(ls.size()-1).getId()+1:1);

                    last=bp;
                    handler.insertBorrow(bp);
                    addDetailButton.setEnabled(true);
                    button.setEnabled(false);

//                    List<Borrow_Pay> ls=handler.getAllBorrowPay();
//                    Toast.makeText(getApplicationContext(),handler.getAllBorrowPayParse().size()+"",Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });



        TableLayout tl = (TableLayout) findViewById(R.id.add_borrow_detail_table);
        /* Create a new row to be added. */
        TableRow trContainer = tl.findViewById(R.id.add_borrow_table_row);

        List<Detailed_Borrow_Pay> detailList=new ArrayList<Detailed_Borrow_Pay>();
        TextView tvi=findViewById(R.id.add_borrow_quantity_input);
        addDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvi.getText().length()==0||Integer.parseInt(tvi.getText().toString())==0){
                    Toast.makeText(getApplicationContext(),"Số lượng phải >0 ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(tvi.getText().toString())>Integer.parseInt(availableQuantity.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Số lượng thiết bị có sẵn là :"+availableQuantity.getText().toString(),Toast.LENGTH_SHORT).show();
                    return;
                }

                for(Detailed_Borrow_Pay detail: detailList){
                    if(deviceSelector.getSelectedItem().toString().split(":")[0].equals(detail.getDeviceId())){
                        Toast.makeText(getApplicationContext(),"Thiết bị đã có trong danh sách mượn",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                TableRow tr=new TableRow(AddBorrowActivity.this);
                TextView tv = new TextView(AddBorrowActivity.this);

                tv.setText(deviceSelector.getSelectedItem().toString().split(":")[0]);
                ((TextView)trContainer.findViewById(R.id.add_borrow_device_column)).measure(0,0);
                tv.setWidth(((TextView)trContainer.findViewById(R.id.add_borrow_device_column)).getMeasuredWidth());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView tv1 = new TextView(AddBorrowActivity.this);
                tv1.setText(tvi.getText().toString());
                ((TextView)trContainer.findViewById(R.id.add_borrow_quantity_column)).measure(0,0);
                tv1.setWidth(((TextView)trContainer.findViewById(R.id.add_borrow_device_column)).getMeasuredWidth());

                tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView tv2 = new TextView(AddBorrowActivity.this);
                tv2.setText(deviceSelector.getSelectedItem().toString().split(":")[1]);
                ((TextView)trContainer.findViewById(R.id.add_borrow_name_column)).measure(0,0);
                tv2.setWidth(((TextView)trContainer.findViewById(R.id.add_borrow_name_column)).getMeasuredWidth());

                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


                Detailed_Borrow_Pay detail=new Detailed_Borrow_Pay(last.getId(),deviceSelector.getSelectedItem().toString().split(":")[0],Integer.parseInt(tvi.getText().toString()),0);
                detailList.add(detail);

                Button button =new Button(AddBorrowActivity.this);
                button.setText("x");
                ((TextView)trContainer.findViewById(R.id.add_borrow_delete_column)).measure(0,0);
                button.setWidth(((TextView)trContainer.findViewById(R.id.add_borrow_delete_column)).getMeasuredWidth());

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tl.removeView(tr);
                        detailList.remove(detail);

                    }
                });
                /* Add TextView to row. */
                tr.addView(tv);
                tr.addView(tv2);
                tr.addView(tv1);
                tr.addView(button);

                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            }


        });
        Button saveDetailBtn=(Button)findViewById(R.id.add_borrow_save_button);
        saveDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailList.size()<=0){
                    Toast.makeText(getApplicationContext(),"Chưa có chi tiết mượn",Toast.LENGTH_SHORT).show();
                    return;
                }
                for(Detailed_Borrow_Pay detail: detailList){
                    handler.insertDetailBorrow((detail));
                }
                //Refresh
                availableQuantity.setText(handler.getAvailableDeviceQuantity(deviceSelector.getSelectedItem().toString().split(":")[0])+"");

                if(tl.getChildCount()>1)
                    tl.removeViews(1,tl.getChildCount()-1);

                detailList.clear();
                last=null;
                addDetailButton.setEnabled(false);
                button.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private Borrow_Pay last;


}