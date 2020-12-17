package com.example.loginpge.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginpge.Data.DatabaseHandler;

import com.example.loginpge.R;
import com.example.loginpge.model.Task;

import java.util.List;

public class Recycletask  extends RecyclerView.Adapter<Recycletask.ViewHolder>{

    public Context context;
    public List<Task> taskList;
    public Activity activity;
    public long setimage;
    public Recycletask( Activity activity,Context context,List<Task>tasklist,long setimage) {
        this.activity=activity;
        this.context=context;
        this.taskList=tasklist;
        this.setimage=setimage;
    }
    public Recycletask(){}

    @NonNull
    @Override
    public Recycletask.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Recycletask.ViewHolder holder, int position) {
        final Task task=taskList.get(position);
        holder.time.setText(String.valueOf(task.getTime()));
        holder.task.setText(String.valueOf(task.getTask()));
        holder.date.setText(String.valueOf(task.getDate()));

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView task;
        TextView date;
        Button done;
        ImageView alaram;
        public RelativeLayout tasker;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.Time);
            task=itemView.findViewById(R.id.task);
            date=itemView.findViewById(R.id.date);
            alaram=itemView.findViewById(R.id.alarm);
            tasker=itemView.findViewById(R.id.task2);
        }
    }

}
