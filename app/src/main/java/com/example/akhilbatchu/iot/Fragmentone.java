package com.example.akhilbatchu.iot;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Fragmentone extends Fragment {


    LocationManager locationManager;
    LocationListener locationListener;
    DatabaseReference mref;
    private FirebaseDatabase mRef;
    ImageView img,Air;
    String as;
    ArrayList<Userdetail> details;
    double latitude, longitude, city;
    int flag=0;
    private FusedLocationProviderClient mFusedLocationClient;
    TextView thermo;


    public Fragmentone() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmentone, container, false);
         img = (ImageView)view.findViewById(R.id.bulb);
         Air = (ImageView)view.findViewById(R.id.Airconditioner);
         thermo = (TextView)view.findViewById(R.id.thermo);
        PagerActivity a = (PagerActivity)getActivity();
        as = a.sendData();
        mref = FirebaseDatabase.getInstance().getReference("users").child(as);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==0) {
                    mref.child("Light").setValue(1);
                    img.setImageResource(R.drawable.ic_light_bulb);
                    flag=1;
                }
                else
                {
                    mref.child("Light").setValue(0);
                    img.setImageResource(R.drawable.ic_bulb);
                    flag=0;

                }
            }
        });
        Air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication() , AirCondtioner.class);
                intent.putExtra("userdetails",as);
                startActivity(intent);
            }
        });


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString());
                Log.i("mylatitude",location.getLatitude() + " ");
                Log.i("mylongitude", location.getLongitude()+ " ");


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }

        };
        if (Build.VERSION.SDK_INT < 23) {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            }

        }
        else {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                // ask for permission

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(lastKnownLocation!=null)
                {
                    latitude = lastKnownLocation.getLatitude();
                    longitude = lastKnownLocation.getLongitude();
                    Log.i("commonbaby",latitude + "");
                    Log.i("commonbaby",longitude + "");

                }


            } else {


                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    // ask for permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);



                }
                else {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }

            }
        }
        PagerActivity activity = (PagerActivity)getActivity();
        String s = activity.sendData();
        mref = FirebaseDatabase.getInstance().getReference("users").child(s);
         activity = (PagerActivity)getActivity();
         s = activity.sendData();
        Log.i("my=string",s);

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Userdetail u = dataSnapshot.getValue(Userdetail.class);
                    Log.i("answer",u.getAnalogSmoke()+"");
                    thermo.setText(u.getTemperature() + "°");
                    if(u.getAnalogSmoke()>1)
                    {Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        Ringtone r = RingtoneManager.getRingtone(getActivity(), notification);
                        r.play();
                        Log.i("hello",""+u.getAnalogSmoke());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    public class Downloadweb extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection connection =null;





            return null;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("working","resume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i("working","onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("working","first");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        }

    }



}
