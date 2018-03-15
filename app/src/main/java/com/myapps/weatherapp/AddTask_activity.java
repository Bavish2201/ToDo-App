package com.myapps.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTask_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final EditText taskEditText = (EditText) findViewById(R.id.et_task);

        Button cancelButton = (Button) findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent();
                setResult(RESULT_CANCELED, goBack);
                finish();
            }
        });

        Button addButton = (Button) findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = String.valueOf(taskEditText.getText());
                //dbHelper.insertTask(task);
                Intent goBack = new Intent();
                goBack.putExtra("TaskDes", task);
                setResult(RESULT_OK, goBack);
                finish();
            }
        });
    }
}
