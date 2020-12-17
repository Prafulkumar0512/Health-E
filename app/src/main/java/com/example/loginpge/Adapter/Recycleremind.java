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
import com.example.loginpge.model.Remind;

import java.util.List;

public class Recycleremind extends RecyclerView.Adapter<Recycleremind.ViewHolder> {
    public Context context;
    public List<Remind> remindList;
    public Activity activity;
    public Recycleremind(Activity activity, Context context, List<Remind>remindlist) {
        this.activity=activity;
        this.context=context;
        this.remindList=remindlist;

    }
    public Recycleremind(){}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.remind,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Remind remind=remindList.get(position);
        holder.Amount.setText("₹"+remind.getAmount());
        holder.Title.setText(remind.getTitle());
        holder.Rdate.setText(remind.getRemind_date());
        holder.ddate.setText(remind.getDue_date());
        holder.billmonth.setText(remind.getMonth1());
    }

    @Override
    public int getItemCount() {
        return remindList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Amount;
        private TextView Title;
        private TextView Rdate;
        private TextView ddate,billmonth;
        private RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Amount=itemView.findViewById(R.id.amount);
            Title=itemView.findViewById(R.id.title);
            Rdate=itemView.findViewById(R.id.rdate);
            ddate=itemView.findViewById(R.id.ddate);
            billmonth=itemView.findViewById(R.id.billmonth);
            layout=itemView.findViewById(R.id.remind);
        }
    }
}
