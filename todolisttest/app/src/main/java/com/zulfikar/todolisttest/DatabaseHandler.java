package com.zulfikar.todolisttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "taskDatabase.db";
    private static final String LIST_TABLE = "listTable";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DATE = "date";
    private static final String TIME ="time";
    private static final String CREATE_LIST_TABLE = "CREATE TABLE " + LIST_TABLE + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + DATE + " TEXT, "
            + TIME + " TEXT)";
    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public String insertTask(String title, String date, String time){
        db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(DATE, date);
        cv.put(TIME, time);
        float result = db.insert(LIST_TABLE, null, cv);

        if (result == -1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }
    }

    public ArrayList<TaskModel> getAllTasks(){
         ArrayList<TaskModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            cursor = db.query(LIST_TABLE,null, null, null, null, null, null, null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHandler.ID)));
                        task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                        task.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                        task.setTime(cursor.getString(cursor.getColumnIndexOrThrow(TIME)));
                        taskList.add(task);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return taskList;
    }

    public void updateTitle(int id, String title){
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        db.update(LIST_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateDate(int id, String date){
        ContentValues cv = new ContentValues();
        cv.put(DATE, date);
        db.update(LIST_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTime(int id, String time){
        ContentValues cv = new ContentValues();
        cv.put(TIME, time);
        db.update(LIST_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(LIST_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }
}
