package com.example.devicemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.devicemanagement.Entities.Device;
import com.example.devicemanagement.Entities.Manager;
import com.example.devicemanagement.Entities.TypeOfDevice;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "devicesManagement";
    // define table name
    private static final String TABLE_MANAGER = "manager";
    private static final String TABLE_STUDENT = "student";
    private static final String TABLE_ROOM = "room";
    private static final String TABLE_TYPE_OF_DEVICE = "typeOfDevice";
    private static final String TABLE_DEVICE = "device";
    private static final String TABLE_BORROW_PAY = "borrowPay";
    private static final String TABLE_DETAILED_BORROW_PAY = "detailedBorrowPay";

    // define common columns
    private static final String KEY_ID = "id";
    private static final String COL_NAME = "name";

    // Manager table - columns
    private static final String COL_PASSWORD = "password";
    private static final String COL_GENDER = "gender";
    private static final String COL_BIRTHDAY = "birthday";

    // Student table - columns
    private static final String COL_CLASS_ID = "classId";

    // Room table - columns
    private static final String COL_FLOOR = "floor";

    // Device table - columns
    private static final String COL_TYPE_OF_DEVICE = "typeOfDeviceId";
    private static final String COL_ORIGIN = "origin";
    private static final String COL_IMAGE = "image";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_STATE = "state";

    // BorrowPay table - columns
    private static final String COL_STUDENT_ID = "studentId";
    private static final String COL_DATE_BORROW = "dateBorrow";
    private static final String COL_ROOM_ID = "roomId";

    // DetailedBorrowPay table - columns
    private static final String COL_DEVICE_ID = "deviceId";
    private static final String COL_BORROW_QUANTITY = "borrowQuantity";
    private static final String COL_PAY_QUANTITY = "payQuantity";

    // Create table statement
    private static final String CREATE_TB_MANAGER = "CREATE TABLE \"manager\" (\n" +
            "\t\"id\"\tTEXT,\n" +
            "\t\"password\"\tTEXT NOT NULL,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"gender\"\tBIT,\n" +
            "\t\"birthday\"\tDATE,\n" +
            "\tPRIMARY KEY(\"id\")\n" +
            ");";

    private static final String CREATE_TB_STUDENT = "CREATE TABLE \"student\" (\n" +
            "\t\"id\"\tTEXT,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"classId\"\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\n" +
            ");";

    private static final String CREATE_TB_ROOM = "CREATE TABLE \"room\" (\n" +
            "\t\"id\"\tTEXT,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"floor\"\tINTEGER,\n" +
            "\tPRIMARY KEY(\"id\")\n" +
            ");";

    private static final String CREATE_TB_TYPE_OF_DEVICE = "CREATE TABLE \"typeOfDevice\" (\n" +
            "\t\"id\"\tTEXT,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\n" +
            ");";

    private static final String CREATE_TB_DEVICE = "CREATE TABLE \"device\" (\n" +
            "\t\"id\"\tTEXT,\n" +
            "\t\"name\"\tINTEGER NOT NULL,\n" +
            "\t\"typeOfDeviceId\"\tTEXT,\n" +
            "\t\"origin\"\tTEXT,\n" +
            "\t\"image\"\tBLOB,\n" +
            "\t\"quantity\"\tINTEGER NOT NULL CHECK(\"quantity\" > 0),\n" +
            "\t\"state\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY(\"typeOfDeviceId\") REFERENCES \"typeOfDevice\"(\"id\")\n" +
            ");";

    private static final String CREATE_TB_BORROW_PAY = "CREATE TABLE \"borrowPay\" (\n" +
            "\t\"id_borrowPay\"\tINTEGER,\n" +
            "\t\"studentId\"\tINTEGER NOT NULL,\n" +
            "\t\"borrowDate\"\tTEXT NOT NULL,\n" +
            "\t\"roomId\"\tINTEGER,\n" +
            "\tPRIMARY KEY(\"id_borrowPay\"),\n" +
            "\tFOREIGN KEY(\"roomId\") REFERENCES \"room\"(\"id\"),\n" +
            "\tFOREIGN KEY(\"studentId\") REFERENCES \"student\"(\"id\")\n" +
            ");";

    private static final String CREATE_TB_DETAILED_BORROW_PAY = "CREATE TABLE \"detailedBorrowPay\" (\n" +
            "\t\"id_BorrowPay\"\tINTEGER,\n" +
            "\t\"deviceId\"\tTEXT,\n" +
            "\t\"borrowQuantity\"\tINTEGER NOT NULL CHECK(\"borrowQuantity\" > 0),\n" +
            "\t\"payQuantity\"\tINTEGER CHECK(\"payQuantity\" >= 0),\n" +
            "\tPRIMARY KEY(\"id_BorrowPay\",\"deviceId\"),\n" +
            "\tFOREIGN KEY(\"id_BorrowPay\") REFERENCES \"borrowPay\"(\"id_borrowPay\"),\n" +
            "\tFOREIGN KEY(\"deviceId\") REFERENCES \"device\"(\"id\")\n" +
            ");";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_MANAGER);
        db.execSQL(CREATE_TB_STUDENT);
        db.execSQL(CREATE_TB_ROOM);
        db.execSQL(CREATE_TB_TYPE_OF_DEVICE);
        db.execSQL(CREATE_TB_DEVICE);
        db.execSQL(CREATE_TB_BORROW_PAY);
        db.execSQL(CREATE_TB_DETAILED_BORROW_PAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANAGER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BORROW_PAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILED_BORROW_PAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE_OF_DEVICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE);

        // Create tables again
        onCreate(db);
    }

    public List<Manager> getAllManagers(){
        List<Manager> managerList = new ArrayList<Manager>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MANAGER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Manager manager = new Manager();
                manager.setId(cursor.getString(0));
                manager.setPassword(cursor.getString(1));
                manager.setName(cursor.getString(2));
                manager.setGender(Boolean.parseBoolean(cursor.getString(3)));
                manager.setBirthday(Date.valueOf(cursor.getString(4)));

                // Adding contact to list
                managerList.add(manager);
            } while (cursor.moveToNext());
        }

        // return manager list
        return managerList;
    }

    public List<TypeOfDevice> getAllTypeOfDevice(){
        List<TypeOfDevice> typeOfDeviceList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE_OF_DEVICE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TypeOfDevice typeOfDevice = new TypeOfDevice();
                typeOfDevice.setId(cursor.getString(0));
                typeOfDevice.setName(cursor.getString(1));
                // Adding contact to list
                typeOfDeviceList.add(typeOfDevice);
            } while (cursor.moveToNext());
        }

        // return types list
        return typeOfDeviceList;
    }

    public List<Device> getAllDevice(){
        List<Device> deviceList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setId(cursor.getString(0));
                device.setName(cursor.getString(1));
                // Adding contact to list
                deviceList.add(device);
            } while (cursor.moveToNext());
        }

        // return device list
        return deviceList;
    }

    public void saveManager(Manager manager){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, manager.getId());
        values.put(COL_PASSWORD, manager.getPassword());
        values.put(COL_NAME, manager.getName());
        values.put(COL_GENDER, manager.isGender());
        values.put(COL_BIRTHDAY, manager.getBirthday());

        db.insert(TABLE_MANAGER, null, values);
        db.close();
    }
}