package com.example.devicemanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Entities.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DevicePaysActivity extends AppCompatActivity {
    TextView edittext;
    Spinner roomSelector;
    DatabaseHandler handler;
    TableRow trContainer;
    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_pays);


      handler = new DatabaseHandler(this);

        LinearLayout rootLayout=findViewById(R.id.device_pays_layout);
        tl = (TableLayout) findViewById(R.id.device_pays_table);
        /* Create a new row to be added. */
        trContainer = tl.findViewById(R.id.device_pays_row);




        List<String> ls1=new ArrayList<String>();
        for(Room room: handler.getAllRoom()){
            ls1.add(room.getId()+":"+room.getName());
        }
        roomSelector=findViewById(R.id.device_pays_select_room_spinner);
        ArrayAdapter<String> roomListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ls1);
        roomSelector.setAdapter(roomListAdapter);


        final Calendar myCalendar = Calendar.getInstance();
        edittext= ( TextView) findViewById(R.id.device_pays_date_picker);
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
                new DatePickerDialog(com.example.devicemanagement.DevicePaysActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button findButton=findViewById(R.id.device_pays_find_button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTable();
            }

        });
        loadTable();
    }

    private void loadTable(){
//            tl.removeAllViews();
        if(tl.getChildCount()>1)
            tl.removeViews(1,tl.getChildCount()-1);
            Cursor c = handler.queryAllRoomBorrows(roomSelector.getSelectedItem().toString().split(":")[0],edittext.getText().toString());
            int count=0;
            if (c.moveToFirst()) {
                do {

                    TableRow tr=new TableRow(DevicePaysActivity.this);

                    String borrowId=c.getString(9);
                    String deviceId=c.getString(2);

                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(DevicePaysActivity.this);
                    tv.setText((count+1)+"");
                    ((TextView)trContainer.findViewById(R.id.device_pays_no)).measure(0,0);
                    tv.setWidth(((TextView)trContainer.findViewById(R.id.device_pays_no)).getMeasuredWidth());
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


                    TextView tv1 = new TextView(DevicePaysActivity.this);
                    tv1.setText(c.getString(2));
                    ((TextView)trContainer.findViewById(R.id.device_pays_device_name)).measure(0,0);
                    tv1.setWidth(((TextView)trContainer.findViewById(R.id.device_pays_device_name)).getMeasuredWidth());

                    tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));



                    TextView tv3 = new TextView(DevicePaysActivity.this);
                    tv3.setText(c.getString((7)));
                    Integer borrowQuantity=Integer.parseInt(c.getString((7)));
                    ((TextView)trContainer.findViewById(R.id.device_pays_borrow_quantity)).measure(0,0);
                    tv3.setWidth(((TextView)trContainer.findViewById(R.id.device_pays_borrow_quantity)).getMeasuredWidth());

                    tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView tv4 = new TextView(DevicePaysActivity.this);
                    tv4.setText(c.getString((8)));
                    Integer payQuantity=Integer.parseInt(c.getString((8)));
                    ((TextView)trContainer.findViewById(R.id.device_pays_pay_quantity)).measure(0,0);
                    tv4.setWidth(((TextView)trContainer.findViewById(R.id.device_pays_pay_quantity)).getMeasuredWidth());

                    tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView tv5 = new TextView(DevicePaysActivity.this);
                    tv5.setText(c.getString((1)));
                    ((TextView)trContainer.findViewById(R.id.device_pays_by_student)).measure(0,0);
                    tv5.setWidth(((TextView)trContainer.findViewById(R.id.device_pays_by_student)).getMeasuredWidth());

                    tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    /* Add TextView to row. */

                    Button button =new Button(DevicePaysActivity.this);
                    button.setWidth(((TextView)trContainer.findViewById(R.id.device_pays_no)).getMeasuredWidth());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DevicePaysActivity.this);
                            builder.setTitle("Nhập số lượng thiết bị muốn trả");

// Set up the input
                            final EditText input = new EditText(DevicePaysActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                            input.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_CLASS_NUMBER);
                            builder.setView(input);

// Set up the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Integer payInput=Integer.parseInt(input.getText().toString());
                                    if(borrowQuantity<payInput+payQuantity){
                                        Toast.makeText(getApplicationContext(),"Error: Số lượng trả vượt quá số lượng mượn",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else {
                                        handler.payDeviceBorrow(borrowId,deviceId,payInput+payQuantity);
                                        loadTable();
                                    }

                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }
                    });
                    button.setText("Trả");


//                    tr.addView(tv);
                    tr.addView(button);
                    tr.addView(tv1);

                    tr.addView(tv3);
                    tr.addView(tv4);
                    tr.addView(tv5);
                    tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                } while (c.moveToNext());
            }
            c.close();
    }

}