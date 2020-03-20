package com.hp.trackzonec;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.harishpadmanabh.apppreferences.AppPreferences;
import com.hp.trackzonec.Adapter.RVAdapter;
import com.hp.trackzonec.Retro.Utils;
import com.hp.trackzonec.model.UserDetail;
import com.hp.trackzonec.model.UsersList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double mlat,mlong;
    AppPreferences appPreferences;
    List<UsersList.DriverDetailsBean> usersLists;
    RecyclerView recyclerView;
    View background;
    int cxx,cyy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);

        //setting the title
        toolbar.setTitle("You are found !");

        //placing toolbar in place of actionbar
        //setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appPreferences = AppPreferences.getInstance(this, getResources().getString(R.string.app_name));

        recyclerView=findViewById(R.id.rv);
        Utils utils=new Utils();
        Call<UsersList> usersListCall=utils.getApi().USERS_LIST_CALL();
        usersListCall.enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                if (response.body().getStatus().equalsIgnoreCase("success"))
                {
                  usersLists=response.body().getDriver_details();
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(MapsActivity2.this, RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(horizontalLayoutManagaer);
                    RVAdapter rvAdapter=new RVAdapter(usersLists,MapsActivity2.this);
                //    recyclerView.setAdapter(rvAdapter);
                    recyclerView.setAdapter(new RVA(usersLists,MapsActivity2.this));

                    runLayoutAnimation(recyclerView);

                }
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {

            }
        });

        background = findViewById(R.id.root);
//anim start====================================
        if (savedInstanceState == null) {
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
                            .zoom(14)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                else {
                    Toast.makeText(MapsActivity2.this, "Invalid geo data", Toast.LENGTH_SHORT).show();
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

        int cxx = background.getRight() - getDips(44);
        int cyy = background.getBottom() - getDips(44);
        SharedPreferences sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

      //  int cx=Integer.parseInt(sharedpreferences.getString("cx", null))- getDips(200);
        //int cy=Integer.parseInt(sharedpreferences.getString("cy", null))- getDips(200);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,cxx,cyy,
                // left-getDips(44),
                // top-getDips(44),
                0,
                finalRadius);

        circularReveal.setDuration(400);
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
          //  int cx=Integer.parseInt(sharedpreferences.getString("cx", null));
          //  int cy=Integer.parseInt(sharedpreferences.getString("cy", null));

             int cx = background.getWidth() - getDips(44);
             int cy = background.getBottom() - getDips(44);

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
            circularReveal.setDuration(400);
            circularReveal.start();
        }
        else {
            super.onBackPressed();
        }
    }

    public void logout(View view) {
        startActivity(new Intent(MapsActivity2.this,MenuPage.class));
    }

    class RVA extends RecyclerView.Adapter<RVA.RVVH> {
        List<UsersList.DriverDetailsBean> usersLists;
        Context context;

        public RVA(List<UsersList.DriverDetailsBean> usersLists, Context context) {
            this.usersLists = usersLists;
            this.context = context;
        }

        @NonNull
        @Override
        public RVVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem, parent, false);
            return new RVVH(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull final RVVH holder, final int position) {
            holder.name.setText(usersLists.get(position).getName());
            holder.lat.setText("Latitude : "+usersLists.get(position).getLat());
            holder.log.setText("Longitude : "+usersLists.get(position).getLog());
            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cxx=holder.mview.getRight();
                    cyy=holder.mview.getBottom();

                    int[] location =new int[2];

                    holder.mview.getLocationOnScreen(location);

                  appPreferences.saveInt("3cxx",location[0]);
                    appPreferences.saveInt("3cyy",location[1]);

                 //   appPreferences.saveInt("3cxx",Integer.parseInt(holder.mview.getX()+holder.mview.getWidth()/2));

                    String uid=usersLists.get(position).getId();
                    String name=usersLists.get(position).getName();
                    Toast.makeText(context, "uid"+uid, Toast.LENGTH_SHORT).show();
                    Log.e("UID",uid);
                    Log.e("FROM APP PREFERENCES",appPreferences.getData("uidshared"));
                    Toast.makeText(context, ""+appPreferences.getData("uidshared"), Toast.LENGTH_SHORT).show();
                    appPreferences.saveData("uidshared",uid);
                    appPreferences.saveData("nameshared",name);
                    Intent i=new Intent(MapsActivity2.this,MapsActivity3.class);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return usersLists.size();
        }


        class RVVH extends  RecyclerView.ViewHolder{
            View mview;
            TextView name,lat,log;

            public RVVH(@NonNull View itemView) {
                super(itemView);
                mview=itemView;
                name=itemView.findViewById(R.id.username);
                lat=itemView.findViewById(R.id.userlat);
                log=itemView.findViewById(R.id.userlong);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();


//            appPreferences.saveInt("3cxx",Integer.parseInt(event.getX()));
//            appPreferences.saveInt("3cyy",cyy);

        }

        return super.onTouchEvent(event);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_recycler_view);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
