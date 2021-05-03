package com.example.devicemanagement;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DeviceBorrowsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_borrows);

        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        if (b != null)
            value = b.getString("deviceId");

        DatabaseHandler handler = new DatabaseHandler(this);

        LinearLayout rootLayout=findViewById(R.id.deviceBorrowsLayout);
        TableLayout tl = (TableLayout) findViewById(R.id.deviceBorrowsTable);
        /* Create a new row to be added. */
        TableRow trContainer = tl.findViewById(R.id.tableRow);




        Cursor c = handler.queryAllDeviceBorrows(value);
        int count=0;
        if (c.moveToFirst()) {
            do {
                TableRow tr=new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


                TextView tv = new TextView(this);
                tv.setText((count+1)+"");
                ((TextView)trContainer.findViewById(R.id.no)).measure(0,0);
                tv.setWidth(((TextView)trContainer.findViewById(R.id.no)).getMeasuredWidth());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView tv1 = new TextView(this);
                tv1.setText(c.getString(2));
                ((TextView)trContainer.findViewById(R.id.roomName)).measure(0,0);
                tv1.setWidth(((TextView)trContainer.findViewById(R.id.roomName)).getMeasuredWidth());

                tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView tv2 = new TextView(this);
                tv2.setText(c.getString((4)));
                ((TextView)trContainer.findViewById(R.id.borrowDate)).measure(0,0);
                tv2.setWidth(((TextView)trContainer.findViewById(R.id.borrowDate)).getMeasuredWidth());

                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView tv3 = new TextView(this);
                tv3.setText(c.getString((5)));
                ((TextView)trContainer.findViewById(R.id.borrowQuantity)).measure(0,0);
                tv3.setWidth(((TextView)trContainer.findViewById(R.id.borrowQuantity)).getMeasuredWidth());

                tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView tv4 = new TextView(this);
                tv4.setText(c.getString((6)));
                ((TextView)trContainer.findViewById(R.id.payQuantity)).measure(0,0);
                tv4.setWidth(((TextView)trContainer.findViewById(R.id.payQuantity)).getMeasuredWidth());

                tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView tv5 = new TextView(this);
                tv5.setText(c.getString((1)));
                ((TextView)trContainer.findViewById(R.id.byStudent)).measure(0,0);
                tv5.setWidth(((TextView)trContainer.findViewById(R.id.byStudent)).getMeasuredWidth());

                tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                /* Add TextView to row. */
                tr.addView(tv);
                tr.addView(tv1);
                tr.addView(tv2);
                tr.addView(tv3);
                tr.addView(tv4);
                tr.addView(tv5);
                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            } while (c.moveToNext());
        }
        c.close();


        c=handler.getDeviceInfo(value);
        if (c.moveToFirst()) {
            do {
                // Passing values
                ((TextView)rootLayout.findViewById(R.id.deviceName)).setText(c.getString(1));
                ((TextView)rootLayout.findViewById(R.id.deviceType)).setText(c.getString(2));
                ((TextView)rootLayout.findViewById(R.id.deviceOrigin)).setText(c.getString(3));
                ((TextView)rootLayout.findViewById(R.id.deviceQuantity)).setText(c.getString(5));
                ((TextView)rootLayout.findViewById(R.id.deviceState)).setText(c.getString(6));

                // Do something Here with values
            } while (c.moveToNext());
        }
        c.close();
        }

}