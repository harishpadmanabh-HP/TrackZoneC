package com.hp.trackzonec;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundUpdateService extends Service implements     GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener

    {
        GoogleApiClient mGoogleApiClient;
        Location mLastLocation;
        LocationRequest mLocationRequest;

    private static Timer timer = new Timer();
    private Context ctx;
        private double mlat,mlong;

        public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        startService();
        //locstart====================================
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
//=====================end loc
    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }

        @Override
        public void onConnected(@Nullable Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            Log.e("LOC SERVICE", String.valueOf(location));
            //  _progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Latitude: " + String.valueOf(mLastLocation.getLatitude())
                    +"Longitude: " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
            mlat=mLastLocation.getLatitude();
            mlong=mLastLocation.getLongitude();
        }

        private class mainTask extends TimerTask
    {
        public void run()
        {
            toastHandler.sendEmptyMessage(0);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
           // Toast.makeText(getApplicationContext(), "LAT "+mlat+" LONG "+mlong, Toast.LENGTH_SHORT).show();
//getLocation();




        }
    };
        public void getLocation() {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            } else {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                /*Getting the location after aquiring location service*/
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);

                if (mLastLocation != null) {
                    //     _progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Latitude: " + String.valueOf(mLastLocation.getLatitude())
                            +"Longitude: " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
                    mlat=mLastLocation.getLatitude();
                    mlong=mLastLocation.getLongitude();
                    //_latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
                    //_longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                } else {
                    /*if there is no last known location. Which means the device has no data for the loction currently.
                     * So we will get the current location.
                     * For this we'll implement Location Listener and override onLocationChanged*/
                    Log.i("Current Location", "No data for location found");

                    if (!mGoogleApiClient.isConnected())
                        mGoogleApiClient.connect();

                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                }
            }
        }

    }