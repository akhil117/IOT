package com.example.akhilbatchu.iot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    String mail,pass,e;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.logemail);
        password = (EditText)findViewById(R.id.logpassword);

        mAuth = FirebaseAuth.getInstance();






    }


    public void onTextPressed(View view)
    {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
        finish();
    }

    public void onForgotPressed(View view)
    {
        startActivity(new Intent(MainActivity.this,Forgot.class));
    }
    public void onButtonPressed(View view)
    {
        mail = email.getText().toString();
        pass = password.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("myemail",mail);
        bundle.putString("mypassword",pass);
        Fragmentone one = new Fragmentone();
        one.setArguments(bundle);
        view.startAnimation(buttonClick);

        if(!(TextUtils.isEmpty(pass) && TextUtils.isEmpty(mail)))
        {
            Intent intent =getIntent();
            int n = mail.length()-10;
            e = mail.substring(0,Math.min(mail.length(),n));
           final String data = e + pass;
            mAuth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(getApplicationContext(),PagerActivity.class);
                                intent.putExtra("data",data);
                                startActivity(intent);
                                makeToast("success");


                            } else {

                                makeToast("failure");


                            }

                        }
                    });
        }

    }

    public void makeToast(String s)
    {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
    }
}
