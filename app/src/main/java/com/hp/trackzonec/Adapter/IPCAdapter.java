package com.hp.trackzonec.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hp.trackzonec.R;
import com.hp.trackzonec.model.IPCModel;

public class IPCAdapter  extends RecyclerView.Adapter<IPCAdapter.IPCVH> {

    IPCModel ipcModel;

    public IPCAdapter(IPCModel ipcModel) {
        this.ipcModel = ipcModel;
    }

    @NonNull
    @Override
    public IPCVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchview= LayoutInflater.from(parent.getContext()).inflate(R.layout.ipclistitem, parent, false);

        return new IPCVH(searchview);
    }

    @Override
    public void onBindViewHolder(@NonNull IPCVH holder, int position) {

        holder.title.setText(ipcModel.getSection_details().get(position).getTitle());
        holder.sec.setText(ipcModel.getSection_details().get(position).getSection_no());
        holder.desc.setText(ipcModel.getSection_details().get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return ipcModel.getSection_details().size();
    }

    static class IPCVH extends RecyclerView.ViewHolder{

        TextView sec,title,desc;

        public IPCVH(@NonNull View itemView) {
            super(itemView);
            sec=itemView.findViewById(R.id.section);
            title=itemView.findViewById(R.id.ipctitle);
            desc=itemView.findViewById(R.id.ipcdesc);

        }
    }
}
