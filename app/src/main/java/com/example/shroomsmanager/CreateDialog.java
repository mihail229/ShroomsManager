package com.example.shroomsmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CreateDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_dialog, null);

        DBHelper DB = new DBHelper(getContext());

        EditText name = (EditText) view.findViewById(R.id.createDialogName);
        EditText date = (EditText) view.findViewById(R.id.createDialogDate);

        builder.setView(view)
                .setTitle("Neue Pilzzucht anlegen")
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Erstellen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strName = name.getText().toString();
                        String strDate = date.getText().toString();
                        System.out.println(strName + strDate);
                        Boolean checkInsert = DB.insertShrooms(strName, strDate);
                        if(checkInsert) Toast.makeText(getContext(), "Pilzzucht erstellt", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getContext(), "Erstellung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return builder.create();
    }
}
