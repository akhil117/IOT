package com.example.akhilbatchu.iot;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class PagerActivity extends AppCompatActivity {

        ViewPager viewPager;
        TabLayout tabLayout;
        String data;
        Adapter adapter;
        mylistner listner;
        OnFragmentInteractionListener listener;

        Toolbar tool;




    public interface OnFragmentInteractionListener {
        void onFragmentCreated (String data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        tool = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        viewPager = (ViewPager)findViewById(R.id.pager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //try
      adapter =new Adapter(getSupportFragmentManager());

        adapter.addFragment(new Fragmentone(),"one");
        adapter.addFragment(new Fragmenttwo(),"two");
        Intent intent = getIntent();
        data = intent.getStringExtra("data");

        viewPager.setOffscreenPageLimit(2);




        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout)findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1)
                {
                    Fragmenttwo fragmenttwo = new Fragmenttwo();



                }
                else if(position==0)
                {
                    Fragmentone one = new Fragmentone();

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public String sendData()
    {
        return  data;
    }



}
