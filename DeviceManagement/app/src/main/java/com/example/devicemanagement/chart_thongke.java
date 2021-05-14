package com.example.devicemanagement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Entities.Borrow_Pay;
import com.example.devicemanagement.Entities.Detailed_Borrow_Pay;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chart_thongke extends AppCompatActivity {
    List<BarEntry> listYear;
    BarChart chart;
    Button chartbut;

    public static final int[] FIVE_COLOR_BAR = {
            Color.rgb(192,255,140), Color.rgb(43,100,140),
            Color.rgb(192,100,20),
            Color.rgb(50,255,140), Color.rgb(50,20,140),
            Color.rgb(50,50,50), Color.rgb(10,10,200)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_thongke);

        setControl();
        setData_chart();
        setEven();
    }

    public void setEven(){
        chartbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chart_thongke.this, StatisticByYearActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setControl(){
        chartbut = findViewById(R.id.back_barchar);
    }

    public static int getYear_ofBorrowday(Date date){
        int year = 0;
        String stringDate = date.toString().substring(date.toString().length()-4);
        year = Integer.parseInt(stringDate);
        return year;
    }

    public void setData_chart(){

        //save data of chart
        listYear = new ArrayList<>();

        DatabaseHandler handler = new DatabaseHandler(this);
        // Test list of devices
        List<Borrow_Pay> borrowPaylist = handler.getAllBorrowPay();
        List<Detailed_Borrow_Pay> detaileds = handler.getAllDetailedBorrowPay();
        List<Integer> years = new ArrayList<>();

        ///get year
        int year_now = 0;
        for (int count = 0; count < borrowPaylist.size(); count++) {
            Borrow_Pay borrow_pay = borrowPaylist.get(count);
            int yearNow = 0;
            year_now = getYear_ofBorrowday(borrow_pay.getBorrowDay());
            years.add(year_now);
        }

        ///delete duplicate of year.
        int sizeOfYears = years.size();
        for (int count = 0 ; count < sizeOfYears-1; count++){
            for (int count1 = count+1; count1 < sizeOfYears; count1++){
                if (years.get(count).compareTo((int) years.get(count1))==0){
                    years.remove(count1);
                    count1--;
                    sizeOfYears--;
                }
//                else System.err.println(years.get(count) + "- count : count1 - " + years.get(count1));
            }
        }

        // test years duplicate
//        for (int count = 0; count < years.size(); count++) {
//            System.err.println(years.get(count) + "check");
//        }

        //get idDevice was borrowed in year
        for (int countYear = 0; countYear < years.size(); countYear++){
            int soluong_inyear = 0;
            for(int count = 0; count < borrowPaylist.size(); count++) {
                BarEntry tk;
                Borrow_Pay borrow_pay = borrowPaylist.get(count);
                int temp_year = getYear_ofBorrowday(borrow_pay.getBorrowDay());
                if (temp_year == years.get(countYear)) {
                    for (int count1 = 0; count1 < detaileds.size(); count1++) {
                        ///connect borrow-detailed_borrow
                        Detailed_Borrow_Pay detailed_borrow_pay = detaileds.get(count1);
                        if (detailed_borrow_pay.getId()== borrow_pay.getId()) {
                            soluong_inyear += detailed_borrow_pay.getNumBorrow();
                            System.err.println("year: "+ getYear_ofBorrowday(borrow_pay.getBorrowDay()) + ", so luong: " + detailed_borrow_pay.getNumBorrow());
                        }
                    }
                }
            }
            listYear.add(new BarEntry(years.get(countYear),soluong_inyear));

        }

        ////data set for barChart
        BarDataSet barDataSet = new BarDataSet(listYear,"Lượng mượn thiết bị theo năm");
        BarData data = new BarData(barDataSet);
        barDataSet.setColors(FIVE_COLOR_BAR);

        ///get chart
        chart = findViewById(R.id.barchart);
        chart.animateY(1000);
        chart.setData(data);
        chart.invalidate();

    }

}
