package com.example.shroomsmanager;

import android.database.Cursor;

public class ShroomManagerUtils {

    public static String getStringFromCursor(Cursor cursor){
        cursor.moveToNext();
        return cursor.getString(0);
    }

    public static String[] getArrayFromCursor(Cursor cursor, int column){
        String[] res = new String[cursor.getCount()];
        while(cursor.moveToNext()){
            res[cursor.getPosition()] = cursor.getString(column);
        }
        return res;
    }
}
