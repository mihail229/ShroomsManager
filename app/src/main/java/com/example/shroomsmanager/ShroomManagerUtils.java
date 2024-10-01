package com.example.shroomsmanager;

import android.database.Cursor;

public class ShroomManagerUtils {

    public static String getStringFromCursor(Cursor cursor){
        cursor.moveToNext();
        return cursor.getString(0);
    }

    public static String[][] getArrayFromCursor(Cursor cursor){
        String[][] res = new String[cursor.getColumnCount()][cursor.getCount()];
        while(cursor.moveToNext()){
            for(int i = 0; i < cursor.getColumnCount(); i++){
                res[i][cursor.getPosition()] = cursor.getString(i);
            }
        }
        return res;
    }
}
