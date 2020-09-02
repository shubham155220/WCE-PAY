package com.abc.wcapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDifferActivity extends AppCompatActivity {

    private Button modifyPayment,passbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_differ);
        modifyPayment = findViewById(R.id.btmodifyfee);
        passbook = findViewById(R.id.btpassbook);


        modifyPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDifferActivity.this,AdminCatagoriesActivity.class));
            }
        });
        passbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDifferActivity.this,AdminStudentPassbook.class));
            }
        });
    }
}
