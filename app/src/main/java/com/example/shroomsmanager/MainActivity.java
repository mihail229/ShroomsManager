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
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB.reset();

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
        if(hasFocus) {
            System.out.println("onWindowFocusChanged1");
            refreshShroomList();
        }
    }

    private void openCreateDialog(View view){
        CreateDialog createDialog = new CreateDialog();
        createDialog.show(getSupportFragmentManager(), "Test Dialog");
    }

    private void refreshShroomList(){
        System.out.println("refreshShroomList");
        String[][] shroomsList = ShroomManagerUtils.getArrayFromCursor(DB.getShrooms());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, shroomsList[1]);
        listViewShrooms.setAdapter(arrayAdapter);
        listViewShrooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String shroomName = arrayAdapter.getItem(i);
                System.out.println(shroomName);
                Intent intent = new Intent(MainActivity.this, ShroomActivity.class);
                intent.putExtra("ShroomId", shroomsList[0][i]);
                startActivity(intent);
            }
        });
    }
}