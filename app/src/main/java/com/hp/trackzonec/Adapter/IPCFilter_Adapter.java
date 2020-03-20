package com.hp.trackzonec.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hp.trackzonec.R;
import com.hp.trackzonec.model.IPCFilterModel;

public class IPCFilter_Adapter extends RecyclerView.Adapter<IPCAdapter.IPCVH> {

IPCFilterModel ipcFilterModel;

    public IPCFilter_Adapter(IPCFilterModel ipcFilterModel) {
        this.ipcFilterModel = ipcFilterModel;
    }

    @NonNull
    @Override
    public IPCAdapter.IPCVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchview= LayoutInflater.from(parent.getContext()).inflate(R.layout.ipclistitem, parent, false);

        return new IPCAdapter.IPCVH(searchview);
    }

    @Override
    public void onBindViewHolder(@NonNull IPCAdapter.IPCVH holder, int position) {
        holder.title.setText(ipcFilterModel.getSection_Details().get(position).getTitle());
        holder.sec.setText(ipcFilterModel.getSection_Details().get(position).getSection_no());
        holder.desc.setText(ipcFilterModel.getSection_Details().get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return ipcFilterModel.getSection_Details().size();
    }
}
