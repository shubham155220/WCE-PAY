package com.abc.wcapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminCatagoriesActivity extends AppCompatActivity {
    private TextView general,obc,sc,st,vja ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_catagories);
        general = (TextView)findViewById(R.id.tvgeneral);
        obc = (TextView)findViewById(R.id.tvobc);
        sc = (TextView)findViewById(R.id.tvsc);
        st = (TextView)findViewById(R.id.tvst);
        vja  = (TextView)findViewById(R.id.tvvja);


        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AdminCatagoriesActivity.this ,AdminAddNewPaymentActivity.class);
                intent.putExtra("category","general");
                startActivity(intent);

            }
        });

        obc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AdminCatagoriesActivity.this ,AdminAddNewPaymentActivity.class);
                intent.putExtra("category","obc");
                startActivity(intent);

            }
        });

        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AdminCatagoriesActivity.this ,AdminAddNewPaymentActivity.class);
                intent.putExtra("category","sc");
                startActivity(intent);

            }
        });
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AdminCatagoriesActivity.this ,AdminAddNewPaymentActivity.class);
                intent.putExtra("category","st");
                startActivity(intent);

            }
        });

        vja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AdminCatagoriesActivity.this ,AdminAddNewPaymentActivity.class);
                intent.putExtra("category","vja");
                startActivity(intent);

            }
        });

    }
}
