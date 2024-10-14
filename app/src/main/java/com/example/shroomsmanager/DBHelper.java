package com.example.shroomsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, "Shrooms.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table shrooms_basic_info(id INTEGER primary key autoincrement, name TEXT,creation_date TEXT, type TEXT)");
        DB.execSQL("create Table shrooms_yields(id INTEGER primary key autoincrement, creation_date TEXT, ertrag TEXT, shroom_id INTEGER, foreign key(shroom_id) references shrooms_basic_info(id)ON DELETE CASCADE \n" +
                "ON UPDATE NO ACTION)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists shrooms_basic_info");
        DB.execSQL("drop Table if exists shrooms_yields");
    }

    public boolean insertShrooms(String name, String creation_date, String type){
        System.out.println("DBHelper.insertShrooms");
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("creation_date", creation_date);
        contentValues.put("type", type);

        long res = DB.insert("shrooms_basic_info", null, contentValues);
        System.out.println(res);
        return (res != -1);
    };

    public boolean updateShrooms(int id, String name, String creation_date, String type){
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("creation_date", creation_date);
        contentValues.put("type", type);
        String[] where = {String.valueOf(id)};
        Cursor cursor = DB.rawQuery("SElECT * from shrooms_basic_info where id = ?", where);
        if(cursor.getCount() > 0){
            long res = DB.update("shrooms_basic_info", contentValues, "id=?", where);
            return (res != -1);
        }
        else return false;
    };

    public boolean deleteShrooms(String id){
        SQLiteDatabase DB = this.getWritableDatabase();

        String[] where = {id};
        Cursor cursor = DB.rawQuery("SElECT * from shrooms_basic_info where id = ?", where);
        if(cursor.getCount() > 0){
            long res = DB.delete("shrooms_basic_info", "id=?", where);
            return (res != -1);
        }
        else return false;
    };

    public Cursor getShrooms(){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SElECT id, name from shrooms_basic_info", new String[] {});
        return cursor;
    }

    public Cursor getShroomsData(String id){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SElECT * from shrooms_basic_info where id=?", new String[] {id});
        return cursor;
    }

    public Cursor getYields(String id){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SElECT * from shrooms_yields where shroom_id=?", new String[] {id});
        return cursor;
    }

    public boolean insertYield(String name, String date, String shroom_id){
        System.out.println("DBHelper.insertYield");
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ertrag", name);
        contentValues.put("creation_date", date);
        contentValues.put("shroom_id", shroom_id);

        long res = DB.insert("shrooms_yields", null, contentValues);
        System.out.println(res);
        return (res != -1);
    }

    public boolean deleteYield(String yieldId){
        System.out.println("DBHelper.deleteYield");
        SQLiteDatabase DB = this.getWritableDatabase();

        String[] where = {yieldId};
        long res = DB.delete("shrooms_yields", "id=?", where);
        return (res != -1);
    }

    public boolean updateYield(int yieldId, String name, String creation_date){
        System.out.println("DBHelper.updateYield");
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ertrag", name);
        contentValues.put("creation_date", creation_date);
        String[] where = {String.valueOf(yieldId)};
        Cursor cursor = DB.rawQuery("SElECT * from shrooms_yields where id = ?", where);
        if(cursor.getCount() > 0){
            long res = DB.update("shrooms_yields", contentValues, "id=?", where);
            return (res != -1);
        }
        else return false;
    }

    public Cursor getYieldData(String yieldId){
        System.out.println("DBHelper.getYieldData");
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SElECT * from shrooms_yields where id=?", new String[] {yieldId});
        return cursor;
    }


    public void reset(){
        onUpgrade(getWritableDatabase(), 0, 0);
        onCreate(getWritableDatabase());
    }

}
