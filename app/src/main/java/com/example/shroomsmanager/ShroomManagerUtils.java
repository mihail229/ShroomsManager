package com.example.shroomsmanager;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

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

    public static String getCurrDate() {
        final Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "."
                + String.valueOf(c.get(Calendar.MONTH) + 1) + "."
                + String.valueOf(c.get(Calendar.YEAR));
    }

}
