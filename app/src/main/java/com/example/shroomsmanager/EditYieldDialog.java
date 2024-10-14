package com.example.shroomsmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;

public class EditYieldDialog extends AppCompatDialogFragment {

    String yieldId, dateString;
    TextView dateView;
    EditText name;
    DBHelper DB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_yield_dialog, null);

        DB = new DBHelper(getContext());

        yieldId = (String) getActivity().getIntent().getSerializableExtra("YieldId");
        System.out.println("ID: " + yieldId);

        name = (EditText) view.findViewById(R.id.createDialogName);
        dateView = (TextView) view.findViewById(R.id.createDialogDate);
        dateView.setText(dateString);

        refreshWindow();

        ImageButton dateButton = view.findViewById(R.id.editDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog();
            }
        });

        builder.setView(view)
                .setTitle("Daten ändern")
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strName = name.getText().toString();
                        String strDate = dateView.getText().toString();
                        System.out.println(strName + strDate);
                        Boolean checkInsert = DB.updateYield(Integer.valueOf(yieldId), strName, strDate);
                        if(checkInsert) Toast.makeText(getContext(), "Ertrag geändert", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getContext(), "Eintragung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return builder.create();
    }

    public void openDateDialog(){
        System.out.println(dateString);
        String[] dateArr = dateString.split("[.]");
        System.out.println("Test " + dateArr[1]);
        DatePickerDialog dateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                dateString = String.valueOf(i2) + "." + String.valueOf(i1+1) + "." + String.valueOf(i);
                dateView.setText(dateString);
                System.out.println("Choosen date:" + dateString);
            }
        }, Integer.valueOf(dateArr[2]), Integer.valueOf(dateArr[1]) -1, Integer.valueOf(dateArr[0]));
        dateDialog.show();
    }

    private void refreshWindow(){
        Cursor cursor = DB.getYieldData(yieldId);
        System.out.println("unpackCursor " + String.valueOf(cursor.getCount()));
        cursor.moveToNext();
        name.setText(cursor.getString(2));
        dateString = cursor.getString(1);
        dateView.setText(dateString);
    }
}
