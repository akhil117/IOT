package com.example.akhilbatchu.iot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AirCondtioner extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    ImageButton img;
    int acflag=0;
    DatabaseReference mref;
    int temperature =18;
    private FirebaseDatabase mRef;
    TextView Temperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_condtioner);

        Intent intent = getIntent();
        Temperature = (TextView)findViewById(R.id.temp);
        String as = intent.getStringExtra("userdetails");
        mref = FirebaseDatabase.getInstance().getReference("users").child(as);

        img = (ImageButton)findViewById(R.id.minus);

    }

    public void powerButton(View view)
    {
        if(acflag==0) {
            acflag=1;
            mref.child("ACPOWER").setValue(acflag);
        }
        else
        {
            acflag=0;
            mref.child("ACPOWER").setValue(acflag);
        }
    }

    public void minuspressed(View view)
    {
        view.startAnimation(buttonClick);
        if(temperature>18) {
            temperature--;
            int val = temperature;
            Temperature.setText(temperature + "°");

            mref.child("AirConditionerTemperature").setValue(temperature);
        }
    }


    public void add(View view)
    {
        view.startAnimation(buttonClick);
        if(temperature<30) {
            temperature++;
            Temperature.setText(temperature + "°");
            mref.child("AirConditionerTemperature").setValue(temperature);
        }
    }
}
