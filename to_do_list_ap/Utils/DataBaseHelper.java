package com.example.to_do_list_ap.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.to_do_list_ap.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TODO_DATABASE";

    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";


    public DataBaseHelper(Context  context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TODO_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS INTEGER)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void insertTask(ToDoModel model){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,model.getTask());
        contentValues.put(COL_3,0);

        db.insert(TABLE_NAME,null,contentValues);
    }

    public void updateTask(int id,String task){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,task);


        //update tha value

        db.update(TABLE_NAME,contentValues,"ID=?", new String[]{String.valueOf(id)});



    }

    public void updateStatus(int id,int status){

        SQLiteDatabase helo = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,status);
        helo.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(id)});




    }

    public void deleteTask(int id){

        SQLiteDatabase helo = this.getWritableDatabase();
        helo.delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(id)});


    }

    @SuppressLint("Range")
    public List<ToDoModel>getAllTasks(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();

        try{

            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);

            if(cursor != null){

                if(cursor.moveToFirst()){
                    do {

                        ToDoModel toDoModel = new ToDoModel();
                        toDoModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        toDoModel.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        toDoModel.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));

                        modelList.add(toDoModel);



                    }while(cursor.moveToNext());
                }

            }
        }finally{

            db.endTransaction();
            cursor.close();
        }
        return  modelList;



    }
}
