package com.abc.wcapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private EditText rusername, rpassword, rfullname, remail;
    private Button rregister;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        rusername = (EditText) findViewById(R.id.etusername);
        rpassword = (EditText) findViewById(R.id.etpassword);
        rfullname = (EditText) findViewById(R.id.etfullname);
        remail = (EditText)findViewById(R.id.etemail);
        rregister = (Button) findViewById(R.id.btregister);
        loading = new ProgressDialog(this);
        rregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccount();
            }
        });

    }

    private void RegisterAccount() {

        String name = rusername.getText().toString();
        String password = rpassword.getText().toString();
        String fullname = rfullname.getText().toString();
        String email = remail.getText().toString();
        if (TextUtils.isEmpty(name)) {

            Toast.makeText(this, "Username field Cannot Be Empty...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Password field Cannot Be Empty...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(fullname)) {

            Toast.makeText(this, "Fullname field Cannot Be Empty...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Email field  Cannot Be Empty...", Toast.LENGTH_SHORT).show();
        } else {

            loading.setTitle("Create Account");
            loading.setMessage("Please wait while we are checking credentials");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            Validateusername(name, password,fullname) ;


        }


    }

    private void Validateusername(final String name, final String password,final String fullname) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(name).exists())){
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("password",password);
                    userdataMap.put("fullname",fullname);
                    RootRef.child("Users").child(name).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegistrationActivity.this,"Your account is created successfully ...",Toast.LENGTH_SHORT).show();
                                        loading.dismiss();
                                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

                                    }
                                    else
                                    {
                                        loading.dismiss();
                                        Toast.makeText(RegistrationActivity.this,"Network error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(RegistrationActivity.this,"This "+ name +" already Exists",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Toast.makeText(RegistrationActivity.this,"Please try with different username ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
