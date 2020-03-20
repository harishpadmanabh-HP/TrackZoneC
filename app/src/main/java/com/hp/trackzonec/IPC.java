package com.hp.trackzonec;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hp.trackzonec.Adapter.IPCAdapter;
import com.hp.trackzonec.Adapter.IPCFilter_Adapter;
import com.hp.trackzonec.Retro.Utils;
import com.hp.trackzonec.model.IPCFilterModel;
import com.hp.trackzonec.model.IPCModel;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IPC extends AppCompatActivity {

    private RecyclerView ipcRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        initView();

        new Utils().getApi().ipcCall().enqueue(new Callback<IPCModel>() {
            @Override
            public void onResponse(Call<IPCModel> call, Response<IPCModel> response) {
                IPCModel ipcModel = response.body();
                if (ipcModel.getStatus().equalsIgnoreCase("success")) {
                    ipcRV.setLayoutManager(new LinearLayoutManager(IPC.this, RecyclerView.VERTICAL, false));

                    ipcRV.setAdapter(new IPCAdapter(ipcModel));
                } else {
                    Toast.makeText(IPC.this, "Cant load sections . Try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IPCModel> call, Throwable t) {
                Toast.makeText(IPC.this, "Cant load sections . Try again later" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView() {
        ipcRV = findViewById(R.id.ipcRV);
    }

    public void fabClick(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.customalert, null);
        dialogBuilder.setView(dialogView);

        EditText searchedt = dialogView.findViewById(R.id.searchedt);
        Button search = dialogView.findViewById(R.id.searchbtn);


        AlertDialog b = dialogBuilder.create();
        b.show();
        search.setOnClickListener(v -> {
            if (searchedt.getText().toString().isEmpty()) {
                b.dismiss();
            } else {

                new Utils().getApi().ipcFilter(searchedt.getText().toString()).enqueue(new Callback<IPCFilterModel>() {
                    @Override
                    public void onResponse(Call<IPCFilterModel> call, Response<IPCFilterModel> response) {
                        IPCFilterModel ipcFilterModel = response.body();
                        if (ipcFilterModel.getStatus().equalsIgnoreCase("success")) {
                            ipcRV.setLayoutManager(new LinearLayoutManager(IPC.this, RecyclerView.VERTICAL, false));
                            ipcRV.setAdapter(new IPCFilter_Adapter(ipcFilterModel));
                            b.dismiss();
                        } else {
                            Toast.makeText(IPC.this, "No sections found with this keyword.", Toast.LENGTH_SHORT).show();

                            b.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<IPCFilterModel> call, Throwable t) {
                        Toast.makeText(IPC.this, "IPC FAILED" + t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
