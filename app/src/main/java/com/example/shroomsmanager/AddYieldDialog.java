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

public class AddYieldDialog extends AppCompatDialogFragment {

    String shroom_id;

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
                        String strDate = date.getText().toString();
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
}
