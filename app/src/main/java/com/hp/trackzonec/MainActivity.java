package com.hp.trackzonec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;

import io.rmiri.buttonloading.ButtonLoading;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.harishpadmanabh.apppreferences.AppPreferences;
import com.hp.trackzonec.Retro.Utils;
import com.hp.trackzonec.model.LocUpdate;
import com.hp.trackzonec.model.Loginmodel;


public class MainActivity extends AppCompatActivity implements
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
        GoogleApiClient mGoogleApiClient;
        Location mLastLocation;
        LocationRequest mLocationRequest;
        EditText pass,email;
        Utils utils;
        String ls;
 AppPreferences appPreferences;
 Double mlat,mlong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
     final ImageView   imageView=(ImageView)findViewById(R.id.logo);
        Animation an2= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        imageView.startAnimation(an2);
        an2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setImageResource(R.drawable.ic_gold_medal);
                Animation an3= AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);

                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 50);
                an3.setInterpolator(interpolator);

                imageView.startAnimation(an3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        appPreferences = AppPreferences.getInstance(this, getResources().getString(R.string.app_name));

        utils=new Utils();

//        Intent serviceIntent=new Intent(this,LocalService.class);
//        startService(serviceIntent);

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

    public void registernow(View view) {

        SharedPreferences       sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("cx", String.valueOf(view.getWidth()));
        editor.putString("cy", String.valueOf(view.getBottom()));
        editor.commit();
     //   startActivity(new Intent(MainActivity.this,Register.class));
       didTapButton(view,new Intent(MainActivity.this,Register.class));

    }
    public void didTapButton(View view, final Intent intent) {
        //Button button = (Button)findViewById(R.id.button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        view.startAnimation(myAnim);
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Toast.makeText(MainActivity.this, "End", Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
       // view.startAnimation(myAnim);
    }

    public void loginclick(View view) {
         Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 50);
        myAnim.setInterpolator(interpolator);

        view.startAnimation(myAnim);


        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Call<Loginmodel> loginmodelCall=utils.getApi().loginCall(email.getText().toString(),pass.getText().toString());
                Log.e("email",email.getText().toString());
                Log.e("pass",pass.getText().toString());
                loginmodelCall.enqueue(new Callback<Loginmodel>() {
                    @Override
                    public void onResponse(Call<Loginmodel> call, Response<Loginmodel> response) {
                        Toast.makeText(MainActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();

                        ls=response.body().getStatus();
                        String uid=response.body().getUser_data().getId();

                        appPreferences.saveData("uid",uid);
                        Log.e("mlat",String.valueOf(mlat));
                        Log.e("mlong",String.valueOf(mlong));

                        Call<LocUpdate> locUpdateCall=utils.getApi().LOC_UPDATE_CALL(mlat,mlong,uid);
                        locUpdateCall.enqueue(new Callback<LocUpdate>() {
                            @Override
                            public void onResponse(Call<LocUpdate> call, Response<LocUpdate> response) {
                                if(response.body().getStatus().equalsIgnoreCase("success")){
                                    Toast.makeText(MainActivity.this, "Location Udated", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "Cannot update location", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LocUpdate> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Cannot update location", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Loginmodel> call, Throwable t) {
                        Toast.makeText(MainActivity.this, ""+t, Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(ls.equalsIgnoreCase("success")){
                    startActivity (new Intent(MainActivity.this,MapsActivity2.class));
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /*Method to get the enable location settings dialog*/
    public void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

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

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
            }
        }
    }

    /*When Location changes, this method get called. */
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //  _progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Latitude: " + String.valueOf(mLastLocation.getLatitude())
                +"Longitude: " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
        mlat=mLastLocation.getLatitude();
        mlong=mLastLocation.getLongitude();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  onCreate(savedInstanceState);

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

    @Override
    protected void onResume() {
        super.onResume();
        // onCreate(savedInstanceState);

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

}
