package com.example.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.sql.Date;
import com.example.devicemanagement.Entities.Manager;

import static android.widget.Toast.LENGTH_SHORT;

public class RegisForm extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton,regis;
    EditText id,name, password, repass;
    RadioButton male,female;
    Button birth;
    DatabaseHandler dbHdl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis_form);
        initDatePicker();
        getSupportActionBar().hide();

        dateButton = findViewById(R.id.btnSpinnerBirth);
        regis=findViewById(R.id.btnRegisF);
        id= findViewById(R.id.etxtIDF);
        name= findViewById(R.id.etxtFullNameF);
        password=findViewById(R.id.etxtPasswordF);
        repass= findViewById(R.id.etxtRePasswordF);
        male= findViewById(R.id.rbtnMale);
        female= findViewById(R.id.rbtnFemale);
        birth=findViewById(R.id.btnSpinnerBirth);


        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               if(!checkEmptyValue(id.getText().toString(), name.getText().toString(), password.getText().toString(), repass.getText().toString())){
                Manager m= getRegAccountIF();
                System.out.print(m.getId()+" "+m.getName()+" "+m.getBirthday()+" "+m.getPassword());
                   if(password.getText().toString().equals(repass.getText().toString()))
                       regisAccount();
                   if (checkEmptyValue(id.getText().toString(), name.getText().toString(), password.getText().toString(), repass.getText().toString())){
                       Toast.makeText(RegisForm.this, "Nhập lại mật khẩu không chính xác", LENGTH_SHORT).show();
                   }
               }
//            }
        });
    }


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return year +"-"+ getMonthFormat(month)+ "-" + day;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "01";
        if(month == 2)
            return "02";
        if(month == 3)
            return "03";
        if(month == 4)
            return "04";
        if(month == 5)
            return "05";
        if(month == 6)
            return "06";
        if(month == 7)
            return "07";
        if(month == 8)
            return "08";
        if(month == 9)
            return "09";
        if(month == 10)
            return "10";
        if(month == 11)
            return "11";
        if(month == 12)
            return "12";

        //default should never happen
        return "T1";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    //sqlite
    private Manager getRegAccountIF(){
        Manager m = new Manager();
        m.setId(id.getText().toString());
        m.setName(name.getText().toString());
        if(male.isChecked()){
            m.setGender(true);
        }
        else{
            m.setGender(false);
        }
        Date date= Date.valueOf(birth.getText().toString());
        m.setBirthday(date);
        m.setPassword(repass.getText().toString());
        return m;
    }

    private boolean checkEmptyValue(String s1, String s2, String s3, String s4){
        if(s1.equals(null)||s2.equals(null)||s3.equals(null)||s4.equals(null)){
            return true;
        }
        return false;
    }
    private void regisAccount(){
        dbHdl= new DatabaseHandler(this);
        Manager m= getRegAccountIF();
        dbHdl.saveManager(m);
        Toast.makeText(this, "Dăng ký thành công", LENGTH_SHORT).show();
        Intent i = new Intent(RegisForm.this, MainActivity.class);
        startActivity(i);
    }
}
