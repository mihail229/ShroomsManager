package com.example.shroomsmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

public class AddYieldDialog extends AppCompatDialogFragment {

    String shroom_id, dateString;
    TextView dateView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_yield_dialog, null);

        DBHelper DB = new DBHelper(getContext());


        EditText name = (EditText) view.findViewById(R.id.createDialogName);
        dateView = (TextView) view.findViewById(R.id.createDialogDate);final Calendar c = Calendar.getInstance();
        dateString = String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "."
                + String.valueOf(c.get(Calendar.MONTH) + 1) + "."
                +String.valueOf(c.get(Calendar.YEAR));
        dateView.setText(dateString);

        ImageButton dateButton = view.findViewById(R.id.editDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog();
            }
        });

        builder.setView(view)
                .setTitle("Neuen Ertrag eintragen")
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strName = name.getText().toString();
                        String strDate = dateView.getText().toString();
                        System.out.println(strName + strDate);
                        Boolean checkInsert = DB.insertYield(strName, strDate, shroom_id);
                        if(checkInsert) Toast.makeText(getContext(), "Ertrag hinzugefügt", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getContext(), "Eintragung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return builder.create();
    }

    public void setShroomId(String id) {
        this.shroom_id = id;
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
}
