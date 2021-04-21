package com.example.devicemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String COL_TYPE_OF_DEVICE = "typeOfDevice";
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
    private static final String CREATE_TB_MANAGER = "CREATE TABLE " + TABLE_MANAGER + "(" + KEY_ID
            + " TEXT PRIMARY KEY, " + COL_PASSWORD + " TEXT NOT NULL, " + COL_NAME + " TEXT NOT NULL, "
            + COL_GENDER + " BIT, " + COL_BIRTHDAY + " DATE)";

    private static final String CREATE_TB_STUDENT = "CREATE TABLE " + TABLE_STUDENT + "(" + KEY_ID
            + " TEXT PRIMARY KEY, " + COL_NAME + " TEXT NOT NULL, " + COL_CLASS_ID + " TEXT NOT NULL)";

    private static final String CREATE_TB_ROOM = "CREATE TABLE " + TABLE_ROOM + "(" + KEY_ID
            + " TEXT PRIMARY KEY, " + COL_NAME + " TEXT NOT NULL, " + COL_FLOOR + " INTEGER)";

    private static final String CREATE_TB_TYPE_OF_DEVICE = "CREATE TABLE " + TABLE_TYPE_OF_DEVICE
            + "(" + KEY_ID + " TEXT PRIMARY KEY, " + COL_NAME + " TEXT NOT NULL) ";

    private static final String CREATE_TB_DEVICE = "CREATE TABLE " + TABLE_DEVICE + "(" + KEY_ID
            + " TEXT PRIMARY KEY, " + COL_NAME + " TEXT NOT NULL, " + COL_TYPE_OF_DEVICE + " TEXT NOT NULL, "
            + COL_ORIGIN + "TEXT, " + COL_IMAGE + " BLOB, " + COL_QUANTITY + " INTEGER NOT NULL, "
            + COL_STATE + "TEXT, FOREIGN KEY(" + COL_TYPE_OF_DEVICE + ") REFERENCES " + TABLE_TYPE_OF_DEVICE
            + "(id)" + ")";

    private static final String CREATE_TB_BORROW_PAY = "CREATE TABLE " + TABLE_BORROW_PAY + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_STUDENT_ID + " TEXT NOT NULL, "
            + COL_DATE_BORROW + " DATE NOT NULL, " + COL_ROOM_ID + "TEXT NOT NULL, "
            + " FOREIGN KEY(" + COL_STUDENT_ID + ") REFERENCES " + TABLE_STUDENT + "(id), "
            + " FOREIGN KEY(" + COL_ROOM_ID + ") REFERENCES " + TABLE_ROOM + "(id)" + ")";

    private static final String CREATE_TB_DETAILED_BORROW_PAY = "CREATE TABLE " + TABLE_DETAILED_BORROW_PAY
            + "(" + KEY_ID + " INTEGER NOT NULL, " + COL_DEVICE_ID + " TEXT NOT NULL, "
            + COL_BORROW_QUANTITY + " INTEGER NOT NULL, " + COL_PAY_QUANTITY + "TEXT, "
            + " FOREIGN KEY(" + KEY_ID + ") REFERENCES " + TABLE_BORROW_PAY + "(id), "
            + " FOREIGN KEY(" + COL_DEVICE_ID + ") REFERENCES " + TABLE_DEVICE + "(id), "
            + " PRIMARY KEY(" + KEY_ID + ", " + COL_DEVICE_ID + ")" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_MANAGER);
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