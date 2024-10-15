package com.example.shroomsmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;

public class EditDialog extends AppCompatDialogFragment {

    String shroomId, dateString;
    TextView dateView;
    EditText name;
    DBHelper DB;
    String[] types = {"Champignons", "Austerpilze", "Lions Mane"};
    String type = types[0];
    ArrayAdapter<String> adapterTypes;
    AutoCompleteTextView autoCompleteTextView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_dialog, null);

        DB = new DBHelper(getContext());

        shroomId = (String) getActivity().getIntent().getSerializableExtra("ShroomId");
        System.out.println("ID: " + shroomId);

        name = (EditText) view.findViewById(R.id.createDialogName);
        dateView = (TextView) view.findViewById(R.id.createDialogDate);
        dateView.setText(dateString);

        autoCompleteTextView = view.findViewById(R.id.auto_complete_text);
        adapterTypes = new ArrayAdapter<String>(getContext(), R.layout.list_item, types);

        autoCompleteTextView.setAdapter(adapterTypes);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
                System.out.println(autoCompleteTextView.getListSelection());
            }
        });

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
                        Boolean checkInsert = DB.updateShrooms(Integer.valueOf(shroomId), strName, strDate, type);
                        if(checkInsert) Toast.makeText(getContext(), "Daten geändert", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getContext(), "Änderung fehlgeschlagen", Toast.LENGTH_SHORT).show();
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
        Cursor cursor = DB.getShroomsData(shroomId);
        System.out.println("unpackCursor " + String.valueOf(cursor.getCount()));
        cursor.moveToNext();
        name.setText(cursor.getString(1));
        dateString = cursor.getString(2);
        dateView.setText(dateString);
        type = cursor.getString(3);
    }
}
