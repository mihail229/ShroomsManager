package com.example.shroomsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton createButton;
    private ListView listViewShrooms;

    DBHelper DB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.activity_main);

        listViewShrooms = (ListView) findViewById(R.id.shroomsList);

        refreshShroomList();

        createButton =(FloatingActionButton) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Create");
                openCreateDialog(view);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        System.out.println("onWindowFocusChanged");
        refreshShroomList();
    }

    private void openCreateDialog(View view){
        CreateDialog createDialog = new CreateDialog();
        createDialog.show(getSupportFragmentManager(), "Test Dialog");
    }

    private void refreshShroomList(){
        String[] shroomsNameList = ShroomManagerUtils.getArrayFromCursor(DB.getShrooms(), 1);
        String[] shroomsIdList = ShroomManagerUtils.getArrayFromCursor(DB.getShrooms(), 0);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, shroomsNameList);
        listViewShrooms.setAdapter(arrayAdapter);
        listViewShrooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String shroomName = arrayAdapter.getItem(i);
                System.out.println(shroomName);
                Intent intent = new Intent(MainActivity.this, ShroomActivity.class);
                intent.putExtra("ShroomId", shroomsIdList[i]);
                startActivity(intent);
            }
        });
    }
}