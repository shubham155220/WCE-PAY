package com.abc.wcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.wcapp.Model.notic;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notice_Create extends AppCompatActivity {
    EditText editText2;
    Button button;
    Spinner Gspinner;
    Button button2;
    DatabaseReference databaseNotic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice__create);
            databaseNotic = FirebaseDatabase.getInstance().getReference("notic");
            editText2 = (EditText) findViewById(R.id.editText2);
            button = (Button) findViewById((R.id.button));
            Gspinner = (Spinner) findViewById(R.id.Gspinner);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send();
                }
            });
        }

        public void send() {
            String name = editText2.getText().toString().trim();
            String gener = Gspinner.getSelectedItem().toString();
            if (!TextUtils.isEmpty(name)) {
                String id = databaseNotic.push().getKey();
                Notic Notic = new Notic(id, name, gener);
                databaseNotic.child(id).setValue(Notic);
                Toast.makeText(this, "Notic added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Enter the Message", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onStart() {
            super.onStart();

        }
    }

