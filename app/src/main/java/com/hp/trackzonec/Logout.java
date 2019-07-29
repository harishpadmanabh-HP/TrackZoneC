package com.hp.trackzonec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        final CircularProgressButton btn = (CircularProgressButton) findViewById(R.id.logout);
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              btn.startAnimation();

          }
      });


    }
}
