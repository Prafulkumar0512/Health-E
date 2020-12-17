package com.example.loginpge.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginpge.R;
import com.example.loginpge.model.Task;

import java.util.List;

public class Waterremainder extends RecyclerView.Adapter<Waterremainder.ViewHolder> {
    public Context context;
    public List<com.example.loginpge.model.Waterremainder> waterList;
    public Activity activity;

    public Waterremainder(){}

    public Waterremainder(Context context, List<com.example.loginpge.model.Waterremainder> waterList, Activity activity) {
        this.context = context;
        this.waterList = waterList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.water,parent,false);
        return new Waterremainder.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final com.example.loginpge.model.Waterremainder wremainder=waterList.get(position);
        holder.time.setText(String.valueOf(wremainder.getTime()));
        holder.date.setText(String.valueOf(wremainder.getDate()));
        holder.waterinml.setText(String.valueOf(wremainder.getWaterml()) + "ml");
    }

    @Override
    public int getItemCount() {
        return waterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout water;
        TextView time,date,waterinml;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            water=itemView.findViewById(R.id.water);
            time=itemView.findViewById(R.id.watertime);
            date=itemView.findViewById(R.id.wdate);
            waterinml=itemView.findViewById(R.id.waterml);
        }
    }
}
