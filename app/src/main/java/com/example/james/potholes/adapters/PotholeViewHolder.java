package com.example.james.potholes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.james.potholes.R;
import com.example.james.potholes.retrofit.model.pothole.Pothole;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by James on 4/15/2017.
 */

public class PotholeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.pothole_text) TextView potholeText;

    public PotholeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(Pothole pothole)
    {
        potholeText.setText(pothole.getPotholeID()+"");
    }
}
