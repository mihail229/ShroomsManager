package com.example.shroomsmanager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShroomActivity extends AppCompatActivity {

    String shroomId, name, date, type;
    TextView shroomNameText, shroomDateText, shroomTypeText;
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

        shroomTypeText = findViewById(R.id.shroomType);
        shroomTypeText.setText(type);

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
        System.out.println("unpackCursor " + String.valueOf(cursor.getCount()));
        cursor.moveToNext();
        name = cursor.getString(1);
        date = cursor.getString(2);
        type = cursor.getString(3);
    }

    private void refreshYieldList(){
        System.out.println("refreshYieldList");
        String[][] yieldList = ShroomManagerUtils.getArrayFromCursor(DB.getYields(shroomId));

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_yield, R.id.textName, yieldList[2]) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                System.out.println("refreshYieldList2");
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(R.id.textName);
                TextView text2 = (TextView) view.findViewById(R.id.textDatum);

                System.out.println("test" + yieldList[0].length + yieldList.length);

                if(yieldList[0].length != 0) {
                    System.out.println("keine Einträge");
                    text1.setText(yieldList[2][position]);
                    text2.setText(yieldList[1][position]);
                }

                Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteYield(yieldList[0][position]);
                        refreshYieldList();
                    }
                });

                ImageButton editButton = (ImageButton) view.findViewById(R.id.editButton);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("EditDialog");
                        openEditDialog();
                    }
                });

                return view;
            }
        };
        listViewYields.setAdapter(adapter);
    }

    private void deleteYield(String yieldId){
        DB.deleteYield(yieldId);
    }

    private void openEditDialog(){
    }

}
