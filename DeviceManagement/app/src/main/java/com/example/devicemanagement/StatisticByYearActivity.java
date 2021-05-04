package com.example.devicemanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Entities.Borrow_Pay;
import com.example.devicemanagement.Entities.Detailed_Borrow_Pay;
import com.example.devicemanagement.Entities.Device;

import java.util.ArrayList;
import java.util.List;

public class StatisticByYearActivity extends AppCompatActivity {
    List<thongTinThongKe> arrThongKe;
    List<thongTinThongKe> arrThongKeNew;
    Button back;
    ListView viewListTK;
    TextView mini_title;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_ke);
        setControl();
        setData();
        setEven();
    }

    public void setEven(){
        adapterListThongke adapter = new adapterListThongke(this,this, arrThongKe, R.layout.rowing_of_table);
        viewListTK.setAdapter(adapter);
    }

    public class data_thongKe{

    }

    public void setData(){
        arrThongKe = new ArrayList<>();
//        arrThongKeNew = new ArrayList<>();
        TextView miniTitle = findViewById(R.id.mini_title);
        DatabaseHandler handler = new DatabaseHandler(this);

        // Test list of devices
        List<Borrow_Pay> borrowPaylist = handler.getAllBorrowPay();
        List<Device> deviceList = handler.getAllDevice();
        List<Detailed_Borrow_Pay> detaileds = handler.getAllDetailedBorrowPay();
//        List<supportThongKe> thongKeList = handler.getAllThongKe();

        if(deviceList.size() >  0 ){
            //get idDevice was borrowed in 2021
            for(int count = 0; count < borrowPaylist.size(); count++){
                thongTinThongKe tk;
                Borrow_Pay borrow_pay = borrowPaylist.get(count);
                ///check in 2021
                if(borrow_pay.getBorrowDay().toString().contains("2021")){
                    for(int count1 = 0; count1 < detaileds.size(); count1++){
                        Detailed_Borrow_Pay detailed_borrow_pay = detaileds.get(count1);

                        if(detailed_borrow_pay.getId()==borrow_pay.getId()){
                             int dem = 0;
                             int sl = 0;
                            for (int i = 0 ; i < deviceList.size() ; i++){

                                if(deviceList.get(i).getId().compareTo(detailed_borrow_pay.getDeviceId())==0){
                                    sl = deviceList.get(i).getQuantity();
                                    dem = i;
                                }
                            }
                            Device device = deviceList.get(dem);
                            tk = new thongTinThongKe("","",0,0);
                            //detailed_borrow_pay.getDeviceId(),deviceList.get(dem).getName(), deviceList.get(dem).getQuantity(), detailed_borrow_pay.getNumBorrow()
                            tk.setSL(deviceList.get(dem).getQuantity());
                            tk.setNameTB(deviceList.get(dem).getName());
                            tk.setId(detailed_borrow_pay.getDeviceId());
                            tk.setInYear(detailed_borrow_pay.getNumBorrow());
                            tk.setSL(device.getQuantity());
                            arrThongKe.add(tk);

                        }
                    }
                }
            }
        }
        else{
            miniTitle.setText("Can't take data from database!!!");
        }
    }

    public void setControl(){
        viewListTK = findViewById(R.id.list_tk);
//        mini_title = findViewById(R.id.mini_title);
    }
}