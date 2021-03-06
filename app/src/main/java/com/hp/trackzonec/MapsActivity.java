package com.hp.trackzonec;

import androidx.fragment.app.FragmentActivity;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.harishpadmanabh.apppreferences.AppPreferences;
import com.hp.trackzonec.Retro.Utils;
import com.hp.trackzonec.model.UserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    AppPreferences appPreferences;
    private GoogleMap mMap;
    View background;
    Double mlat,mlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        appPreferences = AppPreferences.getInstance(this, getResources().getString(R.string.app_name));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //anim start====================================
        if (savedInstanceState == null) {
            background=findViewById(R.id.map);

            background.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                });
            }

        }
//anim end=====================================
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Utils utils=new Utils();
        Call<UserDetail> userDetailCall=utils.getApi().userdetailCall(appPreferences.getData("uid"));
        Log.e("uid------",appPreferences.getData("uid"));

        userDetailCall.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                if(response.body().getStatus().equalsIgnoreCase("success"))
                {
                    mlat=Double.parseDouble(response.body().getDriver_details().get(0).getLat());
                    mlong=Double.parseDouble(response.body().getDriver_details().get(0).getLog());

                    // Add a marker in Sydney and move the camera
                    LatLng sydney = new LatLng(mlat, mlong);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(response.body().getDriver_details().get(0).getName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(sydney)      // Sets the center of the map to Mountain View
                            .zoom(17)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                else {
                    Toast.makeText(MapsActivity.this, "Invalid geo data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {

            }
        });

       }
    private void circularRevealActivity() {
        // SharedPreferences sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        String key;
        String str;
        // int left =Integer.parseInt(sharedpreferences.getString("left", null));

        //  int top=Integer.parseInt(sharedpreferences.getString("top", null));

//        int cx = background.getRight() - getDips(44);
//        int cy = background.getBottom() - getDips(44);
        SharedPreferences sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        int cx=Integer.parseInt(sharedpreferences.getString("cx", null))- getDips(200);
        int cy=Integer.parseInt(sharedpreferences.getString("cy", null))- getDips(200);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,cx,cy,
                // left-getDips(44),
                // top-getDips(44),
                0,
                finalRadius);

        circularReveal.setDuration(900);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();

    }


    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SharedPreferences       sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
            String key;
            String str;
            int cx=Integer.parseInt(sharedpreferences.getString("cx", null));
            int cy=Integer.parseInt(sharedpreferences.getString("cy", null));

            // int cx = background.getWidth() - getDips(44);
            //  int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(500);
            circularReveal.start();
        }
        else {
            super.onBackPressed();
        }
    }
}
