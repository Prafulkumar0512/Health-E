package com.example.loginpge.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginpge.R;
import com.example.loginpge.model.Remind;
import com.example.loginpge.model.Tablelist;

import java.util.List;

public class Paidtable extends RecyclerView.Adapter<Paidtable.ViewHolder> {

    public Context context;
    public List<Tablelist> tablelists;
    public Activity activity;
    public Paidtable(Activity activity, Context context, List<Tablelist>tablelists) {
        this.activity=activity;
        this.context=context;
        this.tablelists=tablelists;

    }
    public Paidtable(){}

    @NonNull
    @Override
    public Paidtable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.table,parent,false);
        return new Paidtable.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Paidtable.ViewHolder holder, int position) {
        holder.Month.setText(tablelists.get(position).getMonth());
        holder.Bill_Title.setText(tablelists.get(position).getBilltitle());
        holder.Amount.setText( "₹"+tablelists.get(position).getAmount());
        holder.Paid_date.setText(tablelists.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return tablelists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Month,Bill_Title,Amount,Paid_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Month=itemView.findViewById(R.id.month);
            Bill_Title=itemView.findViewById(R.id.billtitle);
            Amount=itemView.findViewById(R.id.billamount);
            Paid_date=itemView.findViewById(R.id.pdate);
        }
    }
}
