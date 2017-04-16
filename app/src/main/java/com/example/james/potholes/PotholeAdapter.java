package com.example.james.potholes;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.james.potholes.models.PotholeModel;

/**
 * Created by James on 4/15/2017.
 */

public class PotholeAdapter extends RecyclerView.Adapter<PotholeViewHolder> {

    private PotholeModel potholeModel;

    public PotholeAdapter(PotholeModel potholeModel)
    {
        this.potholeModel = potholeModel;
    }

    public void setPotholeModel(PotholeModel potholeModel)
    {
        this.potholeModel = potholeModel;
    }

    @Override
    public PotholeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.view_holder_pothole, parent, false);
        return new PotholeViewHolder(root);
    }

    @Override
    public void onBindViewHolder(PotholeViewHolder holder, int position) {
        holder.render(potholeModel.getPotholes().get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("pothole",""+potholeModel.getPotholes().size());
        return potholeModel.getPotholes().size();
    }
}
