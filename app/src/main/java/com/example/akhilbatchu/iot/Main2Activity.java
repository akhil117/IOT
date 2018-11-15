package com.example.akhilbatchu.iot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;

public class Main2Activity extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private FirebaseAuth mAuth;
    EditText username, mail, password;
    private FirebaseDatabase mRef;
    String email, un, pass;
    DatabaseReference mref;
    String e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.regemail);
        password = (EditText) findViewById(R.id.regpassword);
        username = (EditText) findViewById(R.id.regusername);
        mRef = FirebaseDatabase.getInstance();


    }


    public void signup(View view)
    {view.startAnimation(buttonClick);
        email = mail.getText().toString();
        pass = password.getText().toString();
        un = username.getText().toString();
        createAccount(email, pass, un);
        int n = email.length()-10;
         e = email.substring(0,Math.min(email.length(),n));
        mref = mRef.getReference("users").child(e+pass);

    }

    public void onAlreadyUser(View view) {
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createAccount(String emai, String pass, String name) {


        if(TextUtils.isEmpty(emai))
        {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            makeToast("enter the password");
            return;
        }
        if(pass.length()<6)
        {
            makeToast("please is too short");
            return;
        }
        final String  p =pass;



            mAuth.createUserWithEmailAndPassword(emai, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                makeToast("sucessfully created.");
                                mref.child("temperature").setValue(0);
                                mref.child("humidity").setValue(0);
                                mref.child("DigitalSmoke").setValue(0);
                                mref.child("AnalogSmoke").setValue(0);
                                mref.child("Light").setValue(0);
                                mref.child("Alarm").setValue(0);
                                mref.child("ACPOWER").setValue(0);
                                mref.child("AirConditionerTemperature").setValue(18);
                                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                                String data = e+p;
                                intent.putExtra("data",data);
                                startActivity(intent);

                            }
                            else
                            {
                                makeToast("Authentication failed.");
                            }

                        }
                    });
        }




    public boolean isInternetAvailable()
    {
        try
        {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");
        }catch (Exception e)
        {
            e.printStackTrace();
            makeToast("Please check internet connection");
            return  false;
        }
    }


    public void makeToast(String s)
    {
        Toast.makeText(Main2Activity.this,s,Toast.LENGTH_LONG).show();
    }
}
