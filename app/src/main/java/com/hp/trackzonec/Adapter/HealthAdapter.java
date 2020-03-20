package com.hp.trackzonec.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.harishpadmanabh.apppreferences.AppPreferences;
import com.hp.trackzonec.HealthIssueDetails;
import com.hp.trackzonec.HealthItemClickListener;
import com.hp.trackzonec.R;
import com.hp.trackzonec.model.HealthIssuesModel;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.HealthVH> {

    HealthIssuesModel  healthIssuesModel;
    Context context;
    private AppPreferences appPreferences;
    HealthItemClickListener healthItemClickListener;

    public HealthAdapter(HealthIssuesModel healthIssuesModel, Context context) {
        this.healthIssuesModel = healthIssuesModel;
        this.context = context;
    }

    @NonNull
    @Override
    public HealthVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchview= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlesearchitem, parent, false);
        appPreferences = AppPreferences.getInstance(context,context.getApplicationContext(). getResources().getString(R.string.app_name));

        return new HealthVH(searchview);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthVH holder, int position) {
      //  final HealthIssuesModel.TipDetailsBean animalItem = healthIssuesModel.getTip_details().get(position);


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH);

        Glide.with(context)
                .asBitmap()
                .load(healthIssuesModel.getTip_details().get(position).getFile())
                // .load(BASE_POSTER_PATH+model.getPoster_path().trim())
                .apply(options)
                .into(new BitmapImageViewTarget(holder.img) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(bitmap, transition);
                        Palette.from(bitmap).generate(palette -> setBackgroundColor(palette, holder));
                    }
                });
       // ViewCompat.setTransitionName(holder.img, animalItem.getTitle());

        holder. name.setText(healthIssuesModel.getTip_details().get(position).getTitle());
        holder.name.setPadding(10,0,0,0);
        holder.itemView.setOnClickListener(v -> {
            appPreferences.saveData("himage",healthIssuesModel.getTip_details().get(position).getFile());
            appPreferences.saveData("hname",healthIssuesModel.getTip_details().get(position).getTitle());
            appPreferences.saveData("hdesc",healthIssuesModel.getTip_details().get(position).getDescription());

            Intent intent = new Intent(context, HealthIssueDetails.class);
            ActivityOptionsCompat optionss = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context,
                            holder.img,
                            ViewCompat.getTransitionName(holder.img));
           context. startActivity(intent, optionss.toBundle());

//
            //healthItemClickListener.onHealthItemClick(position, animalItem, holder.img);

        });


    }
    private void setBackgroundColor(Palette palette, HealthVH holder ) {
        holder.name.setBackgroundColor(palette.getVibrantColor(context
                .getResources().getColor(R.color.black_translucent_60)));
    }

    @Override
    public int getItemCount() {
        return healthIssuesModel.getTip_details().size();
    }


    class HealthVH extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        public HealthVH(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.searchresult_img);
            name=itemView.findViewById(R.id.healthname);

        }
    }
}
