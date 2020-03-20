package com.hp.trackzonec;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hp.trackzonec.Adapter.HealthAdapter;
import com.hp.trackzonec.Retro.Utils;
import com.hp.trackzonec.model.HealthIssuesModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthIssues extends AppCompatActivity  {

    private RecyclerView healthRV;
    public static final String EXTRA_ANIMAL_ITEM = "animal_image_url";
    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "animal_image_transition_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_issues);
        initView();

        new Utils().getApi().healthIssuesCall().enqueue(new Callback<HealthIssuesModel>() {
            @Override
            public void onResponse(Call<HealthIssuesModel> call, Response<HealthIssuesModel> response) {
                HealthIssuesModel healthIssuesModel=response.body();
                if(healthIssuesModel.getStatus().equalsIgnoreCase("success"))
                {
                    healthRV.setLayoutManager(new GridLayoutManager(HealthIssues.this,2));
                    healthRV.setAdapter(new HealthAdapter(healthIssuesModel,HealthIssues.this));
                }else
                {
                    Toast.makeText(HealthIssues.this, "No tips found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<HealthIssuesModel> call, Throwable t) {
                Toast.makeText(HealthIssues.this, "No tips found", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void initView() {
        healthRV = findViewById(R.id.healthRV);
    }

//    @Override
//    public void onHealthItemClick(int pos, HealthIssuesModel.TipDetailsBean healthIssuesModel, ImageView shareImageView) {
//        Intent intent = new Intent(this, HealthIssueDetails.class);
//        intent.putExtra(EXTRA_ANIMAL_ITEM, healthIssuesModel.getFile());
//        intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));
//
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                this,
//                shareImageView,
//                ViewCompat.getTransitionName(shareImageView));
//
//
//        startActivity(intent, options.toBundle());
//    }
}
