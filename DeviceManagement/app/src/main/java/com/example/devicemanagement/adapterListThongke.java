package com.example.devicemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class adapterListThongke extends BaseAdapter {
    private List<thongTinThongKe> arr;
    private Context context;
    private int layout_d;
    private StatisticByYearActivity view;

    public adapterListThongke(Context context,StatisticByYearActivity view, List<thongTinThongKe> arr , int layout_d) {
        this.arr = arr;
        this.context = context;
        this.layout_d = layout_d;
        this.view=view;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =  (LayoutInflater.from(context));
        convertView = inflater.inflate(layout_d, null);

        ///ánh xạ qua view.
        TextView id_table = convertView.findViewById(R.id.id_table);
        TextView name_table = convertView.findViewById(R.id.name_table);
        TextView sl_table = convertView.findViewById(R.id.sl_table);
        TextView inYear_table = convertView.findViewById(R.id.inYear_table);


//        ((TextView)view.findViewById(R.id.id_table)).measure(0,0);
//        id_table.setWidth(((TextView)view.findViewById(R.id.id_table)).getMeasuredWidth());
//
//        ((TextView)view.findViewById(R.id.name_table)).measure(0,0);
//        name_table.setWidth(((TextView)view.findViewById(R.id.name_table)).getMeasuredWidth());
//
//        ((TextView)view.findViewById(R.id.sl_table)).measure(0,0);
//        sl_table.setWidth(((TextView)view.findViewById(R.id.sl_table)).getMeasuredWidth());
//
//        ((TextView)view.findViewById(R.id.inYear_table)).measure(0,0);
//        inYear_table.setWidth(((TextView)view.findViewById(R.id.inYear_table)).getMeasuredWidth());


        //gán giá trị
        thongTinThongKe thongke =arr.get(position);
        id_table.setText(thongke.getId());
        name_table.setText(thongke.getNameTB());
        sl_table.setText(""+thongke.getSL());
        inYear_table.setText(""+thongke.getInYear());
        /// trả về một convertView cho phần gọi phía sau.
        return convertView;
    }
}
