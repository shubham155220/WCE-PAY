package com.abc.wcapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Select_option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
    }
        public void StartF(View view){
            Intent myIntent=new Intent(Select_option.this,AdminCatagoriesActivity.class);
            startActivity(myIntent);

        }
        public void StartC(View view){
            Intent myIntent=new Intent(Select_option.this,Notice_Create.class);
            startActivity(myIntent);
        }
}

