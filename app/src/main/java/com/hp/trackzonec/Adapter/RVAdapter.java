package com.hp.trackzonec.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harishpadmanabh.apppreferences.AppPreferences;
import com.hp.trackzonec.MapsActivity3;
import com.hp.trackzonec.R;
import com.hp.trackzonec.model.UsersList;

import java.util.List;

public class RVAdapter  extends RecyclerView.Adapter<RVAdapter.RVH>{
    List<UsersList.DriverDetailsBean> usersLists;
    Context context;
    AppPreferences appPreferences;

    public RVAdapter(List<UsersList.DriverDetailsBean> usersLists, Context context) {
        this.usersLists = usersLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);
        return new RVH(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RVH holder, final int position) {

        holder.name.setText(usersLists.get(position).getName());
        holder.lat.setText("Latitude : "+usersLists.get(position).getLat());
        holder.log.setText("Longitude : "+usersLists.get(position).getLog());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid=usersLists.get(position).getId();

                SharedPreferences shareddata=context.getSharedPreferences("uidshared", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=shareddata.edit();
                editor.putString("uiduser",uid);
                // appPreferences.saveData("uidfromrv",uid);
             //   Toast.makeText(context, "uid"+uid, Toast.LENGTH_SHORT).show();

                Intent i=new Intent(context,MapsActivity3.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersLists.size();
    }

    class RVH extends RecyclerView.ViewHolder{
        View mview;
        TextView name,lat,log;

        public RVH(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            name=itemView.findViewById(R.id.username);
            lat=itemView.findViewById(R.id.userlat);
            log=itemView.findViewById(R.id.userlong);

        }
    }

}
