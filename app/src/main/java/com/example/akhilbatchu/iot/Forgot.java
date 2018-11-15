package com.example.akhilbatchu.iot;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {

    EditText et;
    private FirebaseAuth auth;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        et = (EditText)findViewById(R.id.inputemail);
        auth = FirebaseAuth.getInstance();
        et = (EditText)findViewById(R.id.inputemail);

    }

    public void resetPassword(View view)
    {
        email = et.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(),"Please enter proper mail",Toast.LENGTH_LONG).show();
            return;
        }
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Forgot.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Forgot.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }
                });
    }



}
