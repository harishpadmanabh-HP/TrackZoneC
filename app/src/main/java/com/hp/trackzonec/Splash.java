package com.hp.trackzonec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView imageView;
    private static int SPLASH_TIME_OUT = 5000;
    Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView=(ImageView)findViewById(R.id.imageViewsplash);
        Animation an2= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        imageView.startAnimation(an2);
        Thread background = new Thread()
        {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(2000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(Splash.this,MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        };

        // start thread
        background.start();
    }


}

