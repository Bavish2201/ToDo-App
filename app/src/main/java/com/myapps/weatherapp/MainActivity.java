package com.myapps.weatherapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayAdapter<String> mArrayAdapter;
    ListView lstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        lstTask = (ListView) findViewById(R.id.lstTask);

        LoadTaskList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTask_activity.class);
                startActivityForResult(intent, 0);
            }
        });
/*
        MenuItem delete_all = (MenuItem) findViewById(R.id.delete_all);
        delete_all.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete All Tasks")
                        .setMessage("Are u sure you want to delete all tasks?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deleteAll();
                                LoadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                alertDialog.show();
                return true;
            }
        });
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK) {
            String task = data.getStringExtra("TaskDes");
            dbHelper.insertTask(task);
            LoadTaskList();
        }
    }

    private void LoadChecks() {


    }

    private void LoadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();

        if(mArrayAdapter == null) {
            mArrayAdapter = new ArrayAdapter<String>(this, R.layout.row, R.id.tsk_title, taskList);
            lstTask.setAdapter(mArrayAdapter);
        }
        else {
            mArrayAdapter.clear();
            mArrayAdapter.addAll(taskList);
            mArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                final EditText taskEditText = new EditText(this);
                taskEditText.setPadding(20, 0, 20, 10);
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Delete All Tasks?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deleteAll();
                                LoadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.tsk_title);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        LoadTaskList();
    }
}
