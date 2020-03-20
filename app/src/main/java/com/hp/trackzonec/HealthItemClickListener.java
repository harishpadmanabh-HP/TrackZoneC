package com.hp.trackzonec;

import android.widget.ImageView;

import com.hp.trackzonec.model.HealthIssuesModel;

public interface HealthItemClickListener {
    void onHealthItemClick(int pos, HealthIssuesModel.TipDetailsBean healthIssuesModel, ImageView shareImageView);

}
