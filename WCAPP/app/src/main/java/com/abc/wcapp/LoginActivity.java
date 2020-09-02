package com.abc.wcapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.wcapp.Model.users;
import com.abc.wcapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText lusername,lpassword;
    private TextView lforgotpassword,ladmin,lnotadmin;
    private CheckBox lbox;
    private Button login;
    private ProgressDialog loading;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lusername = (EditText)findViewById(R.id.etusername);
        lpassword = (EditText)findViewById(R.id.etpasswordlog);
        lforgotpassword = (TextView)findViewById(R.id.tvforpasswordlog);
        ladmin = (TextView)findViewById(R.id.tvadminlog);
        lnotadmin = (TextView)findViewById(R.id.tvnotadminlog);
        lbox = (CheckBox)findViewById(R.id.cbcheckboxlog);
        login = (Button)findViewById(R.id.btloginlog);
        loading = new ProgressDialog(this);
        Paper.init(this);


        login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        }));
        ladmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login Admin");
                ladmin.setVisibility(View.INVISIBLE);
                lnotadmin.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });
        lnotadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login");
                ladmin.setVisibility(View.VISIBLE);
                lnotadmin.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }

    private void LoginUser() {
        String name = lusername.getText().toString();
        String password = lpassword.getText().toString();
        if (TextUtils.isEmpty(name)) {

            Toast.makeText(this, "Username field Cannot Be Empty...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Password field Cannot Be Empty...", Toast.LENGTH_SHORT).show();
        }else{
            loading.setTitle("Login Account");
            loading.setMessage("Please wait  while we are checking credentials");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            AllowAccessToAccount(name ,password);
        }

    }
    private void AllowAccessToAccount(final String name, final String password)
    {
        if(lbox.isChecked()){
            Paper.book().write(Prevalent.usernamekey,name);
            Paper.book().write(Prevalent.passwordkey,password);
        }



        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(name).exists())
                {
                    users userdata =dataSnapshot.child(parentDbName).child(name).getValue(users.class);
                    if(userdata.getName().equals(name))
                    {
                        if(userdata.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this,"Welcome Admin You Are Logged In Successfully...",Toast.LENGTH_SHORT).show();
                                loading.dismiss();

                                startActivity(new Intent(LoginActivity.this,AdminDifferActivity.class));
                            }
                            else if(parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this,"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                Prevalent.currentonlineusers = userdata;

                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }
                        }
                        else{
                            loading.dismiss();
                            Toast.makeText(LoginActivity.this,"Incorrect Password...",Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"Account with this " +name+"  username does not exists",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
