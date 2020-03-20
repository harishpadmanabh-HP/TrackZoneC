package com.hp.trackzonec;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.harishpadmanabh.apppreferences.AppPreferences;

public class HealthIssueDetails extends AppCompatActivity {

    private AppPreferences appPreferences;
    private ImageView hImage;
    private TextView hname;
    private TextView hdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_issue_details);
        appPreferences = AppPreferences.getInstance(this, getResources().getString(R.string.app_name));
//        Bundle extras = getIntent().getExtras();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            String imageTransitionName = extras.getString(HealthIssues.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
//            hImage.setTransitionName(imageTransitionName);
//        }

        initView();
        Glide.with(this).load(appPreferences.getData("himage")).into(hImage);
        hname.setText(appPreferences.getData("hname"));
        hdesc.setText(appPreferences.getData("hdesc"));

    }

    private void initView() {
        hImage = findViewById(R.id.hImage);
        hname = findViewById(R.id.hname);
        hdesc = findViewById(R.id.hdesc);
    }
}
