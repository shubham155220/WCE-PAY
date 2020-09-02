package com.abc.wcapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abc.wcapp.Model.users;
import com.abc.wcapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.ProtectionDomain;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button signup,login;
    private ProgressDialog loading ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup = (Button)findViewById(R.id.btsignup);
        login = (Button)findViewById(R.id.btlogin);
        loading = new ProgressDialog(this);
        Paper.init(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        String usernamekey = Paper.book().read(Prevalent.usernamekey);
        String passwordkey = Paper.book().read(Prevalent.passwordkey);
        if(usernamekey !=""&&passwordkey != "")
        {
            if(!TextUtils.isEmpty(usernamekey)&& !TextUtils.isEmpty(passwordkey))
            {
                AllowAccess(usernamekey,passwordkey);
                loading.setTitle("Already Logged in");
                loading.setMessage("Please wait ...");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
            }
        }
    }

    private void AllowAccess(final String name, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(name).exists())
                {
                    users userdata =dataSnapshot.child("Users").child(name).getValue(users.class);
                    if(userdata.getName().equals(name))
                    {
                        if(userdata.getPassword().equals(password))
                        {
                            //Toast.makeText(MainActivity.this,"Please Wait, You Are Already Logged in",Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            Prevalent.currentonlineusers = userdata;
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }
                        else{
                            loading.dismiss();
                            Toast.makeText(MainActivity.this,"Incorrect Password...",Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Account with this " +name+" is already exists",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
