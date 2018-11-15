package com.example.akhilbatchu.iot;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;


public class Fragmenttwo extends Fragment {
    ArcProgress pb;
    Handler hdlr;
    public int i=0;


    public Fragmenttwo() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmenttwo, container, false);
        pb = (ArcProgress)view.findViewById(R.id.arc_progress);
        pb.setProgress(0);
        hdlr = new Handler();

        Log.i("Created","Done");
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            Call();
        }else
        {

        }
    }



    public void Call()
    {
        i  = pb.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 1;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {
                            pb.setProgress(pb.getProgress() + 1 );
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    
}
