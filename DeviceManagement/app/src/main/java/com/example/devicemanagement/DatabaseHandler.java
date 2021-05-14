package com.example.devicemanagement;

import android.content.ContentValues;
import android.content.Context;
<<<<<<< HEAD
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

=======
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.devicemanagement.Entities.Borrow_Pay;
import com.example.devicemanagement.Entities.Detailed_Borrow_Pay;
import com.example.devicemanagement.Entities.Manager;
import com.example.devicemanagement.Entities.Device;
import com.example.devicemanagement.Entities.TypeOfDevice;
import com.example.devicemanagement.Entities.Room;
import com.example.devicemanagement.Entities.Student;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

>>>>>>> 178139752fc1ac473a4c6534eab03d2f83e94f7f
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
<<<<<<< HEAD
    private static final String COL_TYPE_OF_DEVICE = "typeOfDevice";
=======
    private static final String COL_TYPE_OF_DEVICE = "typeOfDeviceId";
>>>>>>> 178139752fc1ac473a4c6534eab03d2f83e94f7f
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
<<<<<<< HEAD
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
=======
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
>>>>>>> 178139752fc1ac473a4c6534eab03d2f83e94f7f

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_MANAGER);
<<<<<<< HEAD
=======
        db.execSQL(CREATE_TB_STUDENT);
        db.execSQL(CREATE_TB_ROOM);
        db.execSQL(CREATE_TB_TYPE_OF_DEVICE);
        db.execSQL(CREATE_TB_DEVICE);
        db.execSQL(CREATE_TB_BORROW_PAY);
        db.execSQL(CREATE_TB_DETAILED_BORROW_PAY);
>>>>>>> 178139752fc1ac473a4c6534eab03d2f83e94f7f
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

<<<<<<< HEAD
=======
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

>>>>>>> 178139752fc1ac473a4c6534eab03d2f83e94f7f
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
<<<<<<< HEAD
=======
    //update
    public void updateManager(Manager manager) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_PASSWORD, manager.getPassword());
        values.put(COL_NAME, manager.getName());
        values.put(COL_GENDER, manager.isGender());
        values.put(COL_BIRTHDAY, manager.getBirthday());

        db.update(TABLE_MANAGER, values, KEY_ID + " = ?", new String[]{manager.getId()});

    }
    //get Accout Info
    public Manager getAccountIF(String userName, String password){
        Manager account= new Manager();
        SQLiteDatabase sqldb=this. getWritableDatabase();
        Cursor c= sqldb.rawQuery("Select * from "+TABLE_MANAGER+" where id=? and password=?", new String[]{userName,password});
        if(c !=null) {
            if(c.moveToFirst()) {
                account.setId(c.getString(0));
                account.setName(c.getString(2));
            }
        }
        return account;
    }

    // Get Account by ID
    public Manager getAccountById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c= db.rawQuery("Select * from "+TABLE_MANAGER+" where id=?", new String[]{id});
        Manager account= new Manager();
        if (c != null){
            if(c.moveToFirst()) {
                account.setId(c.getString(0));
                account.setPassword(c.getString(1));
                account.setName(c.getString(2));
                account.setGender(Boolean.parseBoolean(c.getString(3)));
                account.setBirthday(Date.valueOf(c.getString(4)));
            }
        }
        return account;
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

    public List<String> getAllTypeOfDeviceId(){
        List<String> typeOfDeviceList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE_OF_DEVICE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                typeOfDeviceList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return types list
        return typeOfDeviceList;
    }

    //get Devices By Type (String)
    public List<Device> getDevicesByType(String type){
        List<Device> deviceList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DEVICE + " WHERE " + COL_TYPE_OF_DEVICE + " = '" + type + "'";
        //        + " WHERE " + COL_TYPE_OF_DEVICE +" = " + typeID ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setId(cursor.getString(0));
                device.setName(cursor.getString(1));
                device.setTypeId(cursor.getString(2));
                device.setOrigin(cursor.getString(3));
                // Adding contact to list
                deviceList.add(device);
            } while (cursor.moveToNext());
        }
        // return device list
        return deviceList;
    }

    public List<Borrow_Pay> getAllBorrowPay(){
        List<Borrow_Pay> borrow_pays = new ArrayList<>();
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_BORROW_PAY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Borrow_Pay borrow_pay = new Borrow_Pay();
                borrow_pay.setId(cursor.getInt(0));
                borrow_pay.setStudentId(cursor.getString(1));
                DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                java.util.Date date = null;
                try {
                    date = format.parse(cursor.getString(2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                borrow_pay.setBorrowDay(date);
                borrow_pay.setRoomId(cursor.getString(3));

                borrow_pays.add(borrow_pay);
            }while(cursor.moveToNext());
        }

        //list be going to return
        return borrow_pays;
    }

    public List<Detailed_Borrow_Pay> getAllDetailedBorrowPay(){
        List<Detailed_Borrow_Pay> detailed_borrow_pays = new ArrayList<>();
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_DETAILED_BORROW_PAY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Detailed_Borrow_Pay detailed = new Detailed_Borrow_Pay();
                detailed.setId(cursor.getInt(0));
                detailed.setDeviceId(cursor.getString(1));
                detailed.setNumBorrow(cursor.getInt(2));
                detailed.setNumPay(cursor.getInt(3));

                detailed_borrow_pays.add(detailed);
            }while(cursor.moveToNext());
        }
        //list be going to return
        return detailed_borrow_pays;
    }

    // Cuong
    public List<Device> getAllDevice(){
        List<Device> deviceList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE + " ORDER BY " + KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setId(cursor.getString(0));
                device.setName(cursor.getString(1));
                device.setTypeId(cursor.getString(2));
                device.setOrigin(cursor.getString(3));

                byte[] imageBytes = cursor.getBlob(4);
                device.setImage(imageBytes);

                device.setQuantity(cursor.getInt(5));
                device.setState(cursor.getString(6));
                // Adding contact to list
                deviceList.add(device);
            } while (cursor.moveToNext());
        }

        // return device list
        return deviceList;
    }

    public Device getDevice(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_DEVICE+" where id=?", new String[]{id});
        Device device = new Device();
        if (cursor != null){
            if(cursor.moveToFirst()) {
                device.setId(cursor.getString(0));
                device.setName(cursor.getString(1));
                device.setTypeId(cursor.getString(2));
                device.setOrigin(cursor.getString(3));

                byte[] imageBytes = cursor.getBlob(4);
                device.setImage(imageBytes);

                device.setQuantity(cursor.getInt(5));
                device.setState(cursor.getString(6));
            }
        }
        return device;
    }

    public void addDevice(Device device){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, device.getId());
        values.put(COL_NAME, device.getName());
        values.put(COL_TYPE_OF_DEVICE, device.getTypeId());
        values.put(COL_ORIGIN, device.getOrigin());

        byte[] imageBytes = new byte[0];
        imageBytes = device.getImage();
        values.put(COL_IMAGE, imageBytes);
        values.put(COL_QUANTITY, String.valueOf(device.getQuantity()));
        values.put(COL_STATE, device.getState());

        db.insert(TABLE_DEVICE, null, values);
        db.close();
    }

    public int updateDevice(Device device){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, device.getName());
        values.put(COL_TYPE_OF_DEVICE, device.getTypeId());
        values.put(COL_ORIGIN, device.getOrigin());

        byte[] imageBytes = new byte[0];
        imageBytes = device.getImage();

        values.put(COL_IMAGE, imageBytes);
        values.put(COL_QUANTITY, device.getQuantity());
        values.put(COL_STATE, device.getState());

        // returns number of rows effected
        return db.update(TABLE_DEVICE, values, KEY_ID + " = ?",
                new String[] {device.getId()});
    }

    public void deleteDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVICE, KEY_ID + " = ?", new String[]{device.getId()});
        db.close();
    }

    public boolean checkIfDeviceInBorrow(Device device){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean hasObject = false;
        Cursor cursor = db.rawQuery("Select * from "+TABLE_DETAILED_BORROW_PAY+" where deviceId=?", new String[]{device.getId()});
        if (cursor != null){
            if(cursor.moveToFirst()) {
                hasObject = true;
            }
        }
        cursor.close();
        db.close();
        return hasObject;
    }

    // Borrow - Pay
    public List<Student> getAllStudent(){
        List<Student> studentsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student=new Student();
                student.setId(cursor.getString(0));
                student.setName(cursor.getString(1));
                student.setClassId(cursor.getString(2));

                // Adding contact to list
                studentsList.add(student);
            } while (cursor.moveToNext());
        }
        // return device list
        return studentsList;
    }

    public List<Room> getAllRoom(){
        List<Room> roomsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Room room =new Room();
                room.setId(cursor.getString(0));
                room.setName(cursor.getString(1));
                room.setFloor(cursor.getInt(2));



                // Adding contact to list
                roomsList.add(room);
            } while (cursor.moveToNext());
        }

        // return device list
        return roomsList;
    }

    public List<Borrow_Pay> getAllBorrowPayParse() throws ParseException {
        List<Borrow_Pay> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BORROW_PAY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Borrow_Pay bp=new Borrow_Pay();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


                bp.setId(cursor.getInt(0));
                bp.setStudentId(cursor.getString(1));
                bp.setBorrowDay(format.parse(cursor.getString(2)));
                bp.setRoomId(cursor.getString(0));

                // Adding contact to list
                list.add(bp);
            } while (cursor.moveToNext());
        }

        // return device list
        return list;
    }


    public void insertBorrow(Borrow_Pay borrow){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_borrowPay", borrow.getId());
        values.put("studentId", borrow.getStudentId());
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        values.put("borrowDate", sdf.format(borrow.getBorrowDay()));
        values.put("roomId", borrow.getRoomId());

        db.insert(TABLE_BORROW_PAY, null, values);
        db.close();
    }
    public void insertDetailBorrow(Detailed_Borrow_Pay detailed_borrow_pay){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_BorrowPay", detailed_borrow_pay.getId());
        values.put("deviceId", detailed_borrow_pay.getDeviceId());
        values.put("payQuantity", 0);
        values.put("borrowQuantity", detailed_borrow_pay.getNumBorrow());

        db.insert(TABLE_DETAILED_BORROW_PAY, null, values);
        db.close();
    }

    public void payDeviceBorrow(String borrowId, String deviceId,int borrowQuan){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("payQuantity", borrowQuan);

        db.update(TABLE_DETAILED_BORROW_PAY,values,"id_BorrowPay=? and deviceId=?", new String[]{borrowId,deviceId});
        db.close();
    }

    public  Cursor getDeviceInfo(String deviceId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT device.id,device.name,typeOfDevice.name,device.origin,device.image,device.quantity,device.state "
                + " FROM device,typeOfDevice "
                + " WHERE device.id='%s' AND device.typeOfDeviceId=typeOfDevice.id";
        Cursor c = db.rawQuery(String.format(query, deviceId), null);
        return c;
    }

    public Cursor queryAllDeviceBorrows(String deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT student.id, student.name, room.name,room.floor, borrowPay.borrowDate, detailedBorrowPay.borrowQuantity,detailedBorrowPay.payQuantity"
                + " FROM borrowPay,student,device,room,detailedBorrowPay"
                + " WHERE borrowPay.studentId=student.id AND  detailedBorrowPay.id_BorrowPay=borrowPay.id_borrowPay AND detailedBorrowPay.deviceId=device.id AND borrowPay.roomId=room.id AND detailedBorrowPay.deviceId='%s'";
        Cursor c = db.rawQuery(String.format(query, deviceId), null);
        return c;
    }

    public Cursor queryAllRoomBorrows(String roomId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT student.id, student.name,device.id, device.name, room.name,room.floor, borrowPay.borrowDate, detailedBorrowPay.borrowQuantity,detailedBorrowPay.payQuantity, borrowPay.id_borrowPay"
                + " FROM borrowPay,student,device,room,detailedBorrowPay"
                + " WHERE borrowPay.studentId=student.id AND  detailedBorrowPay.id_BorrowPay=borrowPay.id_borrowPay AND detailedBorrowPay.deviceId=device.id AND borrowPay.roomId=room.id AND room.id='%s' AND borrowPay.borrowDate='%s'";
        Cursor c = db.rawQuery(String.format(query, roomId,  date), null);
        return c;
    }


    public int getAvailableDeviceQuantity(String deviceId){
        Cursor cursor=getDeviceInfo(deviceId);
        cursor.moveToFirst();
        int available=cursor.getInt(5);
        Cursor c=queryAllDeviceBorrows(deviceId);
        if (c.moveToFirst())
            do{
                available+=c.getInt(6)-c.getInt(5);

            }while (c.moveToNext());
        c.close();
        return available;
    };


>>>>>>> 178139752fc1ac473a4c6534eab03d2f83e94f7f
}