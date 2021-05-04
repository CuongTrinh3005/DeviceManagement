package com.example.devicemanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Entities.Manager;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserLayout extends AppCompatActivity {
    ImageView logo;
    ImageView quat1;
    ImageView quat2;
    ImageView hv;

    Button btNS;
    Button btHoantat;
    Button btCapNhat;
    Button btSuaTen;

    TextView tvHoTen;
    TextView tvMaSo;
    TextView tvMK;
    TextView tvNS;
    RadioButton rdNam;
    RadioButton rdNu;

    //EditText edtName;
    DatabaseHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String id = b.getString("accountId");
        setControl();
        setEvent();
        DatabaseHandler handler = new DatabaseHandler(this);
//        Manager manager = new Manager("MN01", "1234", "Cuong", true,
//                Date.valueOf("1999-03-04"));
//        handler.saveManager(manager);
        Manager manager = handler.getAccountById(id);

        // Manager manager = handler.getAccountIF("abc", "1234");
        //Toast.makeText(UserLayout.this, manager.getName(),Toast.LENGTH_SHORT).show();

        String hoten = "", maso = "";
        String ngaysinh = "", matkhau = "";
        boolean gioitinh;


        maso = manager.getId();
        hoten = manager.getName();
        ngaysinh = manager.getBirthday();
        matkhau = manager.getPassword();
        gioitinh = manager.isGender();

        if (gioitinh == false) {
            rdNu.setChecked(true);
        } else {
            rdNam.setChecked(true);
        }

        tvMaSo.setText(maso);
        tvHoTen.setText(hoten);
        tvNS.setText(ngaysinh);
        tvMK.setText(matkhau);


    }

    private void setControl() {
        logo = (ImageView) findViewById(R.id.logo);
        quat1 = (ImageView) findViewById(R.id.canhquat1);
        quat2 = (ImageView) findViewById(R.id.canhquat2);
        hv = (ImageView) findViewById(R.id.hocvien);

        btNS = (Button) findViewById(R.id.btNS);
        btCapNhat = (Button) findViewById(R.id.btCapNhat);
        btHoantat = (Button) findViewById(R.id.btHoantat);
        btSuaTen = (Button) findViewById(R.id.btSuaTen);

        tvHoTen = (TextView) findViewById(R.id.tvHoTen);
        tvMaSo = (TextView) findViewById(R.id.tvMaSo);
        tvNS = (TextView) findViewById(R.id.tvNS);
        tvMK = (TextView) findViewById(R.id.tvMK);
        rdNam = (RadioButton) findViewById(R.id.rdNam);
        rdNu = (RadioButton) findViewById(R.id.rdNu);


    }

    private void setEvent() {
        setTitle("Thông tin người dùng");

        Animation anim_logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        logo.startAnimation(anim_logo);
        Animation anim_quat1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        quat1.startAnimation(anim_quat1);
        Animation anim_quat2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        quat2.startAnimation(anim_quat2);
        Animation anim_hv = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        hv.startAnimation(anim_hv);

        btCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMatkhau(v);
            }
        });
        btSuaTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHoTen(v);
            }
        });

        btNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        btHoantat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();

            }
        });


    }

    //
    private void ChonNgay() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                tvNS.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }


    public void dialogMatkhau(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View alert = LayoutInflater.from(this).inflate(R.layout.dialogpass, null);

        builder.setView(alert);


        EditText edtMK;
        EditText edtNLMK;


        edtMK = alert.findViewById(R.id.edtMK);
        edtNLMK = alert.findViewById(R.id.edtNLMK);


        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (edtMK.getText().toString().trim().equals("") == true) {
                    Toast.makeText(UserLayout.this, "Chưa nhập mật khẩu", Toast.LENGTH_LONG).show();
                } else if (edtMK.getText().toString().trim().equals(edtNLMK.getText().toString().trim()) == false) {

                    Toast.makeText(UserLayout.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_LONG).show();
                } else {

                    tvMK.setText(edtMK.getText());
                }


            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        // tạo dialog và hiển thị
        builder.setTitle("Mật khẩu:");
        builder.create().show();

    }

    //
    public void dialogHoTen(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View alert = LayoutInflater.from(this).inflate(R.layout.dialoghoten, null);

        builder.setView(alert);


        EditText edtTen;


        edtTen = alert.findViewById(R.id.edtTen);


        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (edtTen.getText().toString().trim().equals("") == true) {
                    Toast.makeText(UserLayout.this, "Vui lòng nhập tên", Toast.LENGTH_LONG).show();
                } else {

                    tvHoTen.setText(edtTen.getText());
                }


            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        // tạo dialog và hiển thị
        builder.setTitle("Họ và tên:");
        builder.create().show();

    }


    private void Update() {
        Manager manager = new Manager();
        manager.setId(tvMaSo.getText().toString());
        manager.setName(tvHoTen.getText().toString());
        if (rdNam.isChecked()) {
            manager.setGender(true);
        } else {
            manager.setGender(false);
        }
        Date date = Date.valueOf(tvNS.getText().toString());
        manager.setBirthday(date);
        manager.setPassword(tvMK.getText().toString());

        DatabaseHandler db = new DatabaseHandler(this);

        db.updateManager(manager);
        Toast.makeText(this, "Cập nhật Xong", Toast.LENGTH_SHORT).show();
    }


}