package com.example.sdvs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private Context context;
    public DbHelper(@Nullable Context context) {

        super(context, "Attendance.db", null, VERSION);
        this.context=context;
    }


    //table name

   public static final String CLASS_INFO_TABLE = "CLASS_TABLE";
   public static final String STUDENT_INFO_TABLE = "STUDENT_TABLE";
    public  static final String STATUS_INFO_TABLE = "STATUS_TABLE";

    //column names
    public static final String KEY_CLASS_ID = "_cid";
   public static final String KEY_CLASS_NAME = "class_name";
    public  static final String KEY_SUBJECT_NAME= "subject_name";


    private static final String KEY_STUDENT_ID = "_student_id";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_STUDENT_ROLL= "student_roll";

    private static final String KEY_STATUS_ID = "_status_id";
    private static final String KEY_STATUS_DATE = "status_date";
    private static final String KEY_STATUS= "status";





    private static final String DROP_CLASS_TABLE = " DROP TABLE IF EXISTS "+ CLASS_INFO_TABLE;
    private static final String DROP_STUDENT_TABLE = " DROP TABLE IF EXISTS "+ STUDENT_INFO_TABLE;
    private static final String DROP_STATUS_TABLE = " DROP TABLE IF EXISTS "+ STATUS_INFO_TABLE;
    private static final String SELECT_CLASS_TABLE = "SELECT * FROM "+CLASS_INFO_TABLE;
    private static  final String SELECT_STUDENT_TABLE = "SELECT * FROM "+STUDENT_INFO_TABLE;
    private static  final String SELECT_STATUS_TABLE = "SELECT * FROM "+STATUS_INFO_TABLE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String queryClass = "CREATE TABLE " + CLASS_INFO_TABLE +
                            " (" + KEY_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            KEY_CLASS_NAME +  " TEXT, " +
                            KEY_SUBJECT_NAME +  " TEXT);";
                            //"UNIQUE (" + KEY_CLASS_NAME + "," + KEY_SUBJECT_NAME + ")" +

          db.execSQL(queryClass);

         */

        String query = "CREATE TABLE " + CLASS_INFO_TABLE +
                " (" + KEY_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_CLASS_NAME + " TEXT, " +
                KEY_SUBJECT_NAME + " TEXT);";
        db.execSQL(query);


        String queryStudent = " CREATE TABLE  "+ STUDENT_INFO_TABLE +
                " (" + KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                KEY_CLASS_ID + " INTEGER NOT NULL, "+
                KEY_STUDENT_NAME + " TEXT NOT NULL, " +
                KEY_STUDENT_ROLL + " INTEGER," +
                "FOREIGN KEY (" + KEY_CLASS_ID +") REFERENCES " + CLASS_INFO_TABLE + "("+KEY_CLASS_ID+")"+
                ");";

          db.execSQL(queryStudent);


        String queryStatus = " CREATE TABLE  "+ STATUS_INFO_TABLE +
                " (" + KEY_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                KEY_STUDENT_ID + " INTEGER NOT NULL, "+
                KEY_STATUS_DATE + " DATE NOT NULL, " +
                KEY_STATUS + " TEXT NOT NULL, " +
                "UNIQUE (" + KEY_STUDENT_ID + "," +KEY_STATUS_DATE + ")," +
                "FOREIGN KEY (" + KEY_STUDENT_ID +") REFERENCES " + STATUS_INFO_TABLE + "("+ KEY_STUDENT_ID +")"+
                ");";


        db.execSQL(queryStatus);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    try{
        db.execSQL(DROP_CLASS_TABLE);
        db.execSQL(DROP_STUDENT_TABLE);
        db.execSQL(DROP_STATUS_TABLE);
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    }



    long addClass(String className,String subjectName)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CLASS_NAME,className);
        cv.put(KEY_SUBJECT_NAME,subjectName);

        return database.insert(CLASS_INFO_TABLE,null,cv);
    }

    Cursor getClassTable()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        if(database!= null) {
            cursor=database.rawQuery(SELECT_CLASS_TABLE, null);
        }
      return cursor;
    }

    public void deleteClass(long cid) {

        SQLiteDatabase database = this.getReadableDatabase();
        //database.delete(CLASS_INFO_TABLE,"_cid=?",new String[]{_cid});
        long result = database.delete(CLASS_INFO_TABLE, "_cid=?", new String[]{String.valueOf(cid)});
        if(result == -1)
        {
            Toast.makeText(context, "failed to delete", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "deleted successfully", Toast.LENGTH_LONG).show();
        }
    }

    public void updateClass(long cid, String className, String subjectName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CLASS_NAME,className);
        cv.put(KEY_SUBJECT_NAME,subjectName);

        long result = database.update(CLASS_INFO_TABLE,cv,"_cid=?",new String[]{String.valueOf(cid)});
        if(result == -1)
        {
            Toast.makeText(context, "failed to update", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "updated successfully", Toast.LENGTH_LONG).show();
        }
    }


    long addStudent(long cid, int roll,String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CLASS_ID,cid);
        cv.put(KEY_STUDENT_ROLL ,roll);
        cv.put(KEY_STUDENT_NAME ,name);
        return database.insert(STUDENT_INFO_TABLE,null,cv);

    }
    Cursor getStudentTable(long cid)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        if(database!= null) {
            //cursor=database.rawQuery(SELECT_STUDENT_TABLE,null,KEY_CLASS_ID+"=?",new String[]{String.valueOf(cid)},null,null,KEY_STUDENT_ROLL);
            cursor = database.query(STUDENT_INFO_TABLE,null,KEY_CLASS_ID+"=?",new String[]{String.valueOf(cid)},null,null, KEY_STUDENT_ROLL);
        }
        return cursor;
    }


    public void deleteStudent(long sid) {

        SQLiteDatabase database = this.getReadableDatabase();
        //database.delete(CLASS_INFO_TABLE,"_cid=?",new String[]{_cid});
        long result = database.delete(STUDENT_INFO_TABLE, "_student_id=?", new String[]{String.valueOf(sid)});
        if(result == -1)
        {
            Toast.makeText(context, "failed to delete", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "deleted successfully", Toast.LENGTH_LONG).show();
        }
    }

    public void updateStudent(long sid, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_SUBJECT_NAME,name);


        long result = database.update(STUDENT_INFO_TABLE,cv,"_student_id=?",new String[]{String.valueOf(sid)});
        if(result == -1)
        {
            Toast.makeText(context, "failed to update", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "updated successfully", Toast.LENGTH_LONG).show();
        }
    }

}
