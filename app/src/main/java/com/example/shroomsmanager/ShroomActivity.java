package com.example.shroomsmanager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShroomActivity extends AppCompatActivity {

    String shroomId, name, date;
    TextView shroomNameText, shroomDateText;
    Button deleteButton, addYieldButton;
    ListView listViewYields;

    DBHelper DB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        System.out.println("onCreate ShroomActivity");
        setContentView(R.layout.activity_shroom);

        shroomId = (String) getIntent().getSerializableExtra("ShroomId");
        System.out.println("ID: " + shroomId);

        unpackCursor(DB.getShroomsData(shroomId));

        listViewYields = findViewById(R.id.listViewYields);
        refreshYieldList();

        shroomNameText = findViewById(R.id.shroomName);
        shroomNameText.setText(name);

        shroomDateText = findViewById(R.id.shroomDate);
        shroomDateText.setText(date);

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.deleteShrooms(shroomId);
                finish();
            }
        });

        addYieldButton = findViewById(R.id.addYieldButton);
        addYieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddYieldDialog addYieldDialog = new AddYieldDialog();
                addYieldDialog.setShroomId(shroomId);
                addYieldDialog.show(getSupportFragmentManager(), "Test Dialog Yield");
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        System.out.println("onWindowFocusChanged");
        refreshYieldList();
    }

    private void unpackCursor(Cursor cursor){
        cursor.moveToNext();
        name = cursor.getString(1);
        date = cursor.getString(2);
    }

    private void refreshYieldList(){
        String[] yieldNameList = ShroomManagerUtils.getArrayFromCursor(DB.getYields(shroomId), 2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ShroomActivity.this,
                android.R.layout.simple_list_item_1, yieldNameList);
        listViewYields.setAdapter(arrayAdapter);
    }

}
