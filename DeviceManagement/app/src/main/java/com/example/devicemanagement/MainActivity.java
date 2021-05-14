package com.example.devicemanagement;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.os.Bundle;

import java.sql.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler handler = new DatabaseHandler(this);
        Manager manager = new Manager("MN01", "1234", "Cuong", true,
                Date.valueOf("1999-03-04"));
        handler.saveManager(manager);
=======
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.devicemanagement.Entities.Manager;
import com.example.devicemanagement.Loading.LoadingDialog;

import java.util.concurrent.Delayed;

public class MainActivity extends AppCompatActivity{
    EditText user, pass;
    CheckBox savePass;
    Button logIn;
    Button regis;
    ImageView logo;
    TextView txtHello;
    Animation topAnim, bottomAnim;

    DatabaseHandler dbHdl;
    long millis=System.currentTimeMillis();
    java.sql.Date date=new java.sql.Date(millis);
//    Manager m= new Manager("123","123", "VA", true,date);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setControl();
        //Anim
        topAnim= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo.setAnimation(topAnim);
        pass.setAnimation(topAnim);
        user.setAnimation(topAnim);

        savePass.setAnimation(bottomAnim);
        logIn.setAnimation(bottomAnim);
        regis.setAnimation(bottomAnim);
        LoadingDialog loadingDialog= new LoadingDialog(MainActivity.this);

//        addManager(m);
        SharedPreferences sharedPreferences= getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox= sharedPreferences.getString("remember","");
        if(checkbox.equals("true")){
            Intent intent= new Intent(MainActivity.this, FirstPage.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Xin mời đăng nhập",Toast.LENGTH_SHORT).show();
        }

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, FirstPage.class);
                String userName= user.getText().toString();
                String password= pass.getText().toString();
                try {
                    boolean isEmpty=checkEmptyAccount(userName,password);
                    Manager account=dbHdl.getAccountIF(userName, password);
                    System.out.println(account.getName());
                    if(!isEmpty) {
                        if (!account.getId().equals("")) {
                            Bundle b = new Bundle();
                            b.putStringArray("account", new String[]{account.getName(), account.getId()});
                            intent.putExtras(b);
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismissDialog();
                                }},3000);
                            loadingDialog.startLoadingDialog();
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception ex){
                    System.err.println(ex.getMessage());
                }
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, RegisForm.class);
                startActivity(intent);
            }
        });

        savePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences sharedPreferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void setControl(){
        user=(EditText) findViewById(R.id.etxtUserName);
        pass= (EditText) findViewById(R.id.etxtPassword);
        savePass= (CheckBox)findViewById(R.id.chbsSavePass);
        logIn=(Button) findViewById((R.id.btnLogIn));
        regis=(Button)findViewById(R.id.btnRegis) ;
        dbHdl=new DatabaseHandler(this);
        logo= findViewById(R.id.logo);
    }
    public boolean checkEmptyAccount(String s1, String s2){
        if(s1.equals("") || s2.equals("")){
            Toast.makeText( MainActivity.this, "Vui lòng nhập đầy đủ thông tin đăng nhập", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public void addManager(Manager manager){
        this.dbHdl.saveManager(manager);
>>>>>>> 178139752fc1ac473a4c6534eab03d2f83e94f7f
    }
}