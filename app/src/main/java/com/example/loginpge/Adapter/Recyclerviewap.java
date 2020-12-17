package com.example.loginpge.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.loginpge.Updateexpensepg;
import com.example.loginpge.R;
import com.example.loginpge.model.Transaction;

import java.util.List;

public class Recyclerviewap extends RecyclerView.Adapter<Recyclerviewap.ViewHolder> {

    private Context context;
    private List<Transaction> translist;
    private Activity activity;

    public Recyclerviewap(Activity activity, Context context, List<Transaction> translist) {
        this.activity=activity;
        this.context = context;
        this.translist = translist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.transaction,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Transaction transaction=translist.get(position);
        holder.Title.setText(String.valueOf(transaction.getTitle()));
        holder.Amount.setText(String.valueOf("₹" + transaction.getAmount()));
        holder.Date.setText(String.valueOf(transaction.getDate()));
        holder.bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Updateexpensepg.class);
                intent.putExtra("id",String.valueOf(transaction.getId()));
                intent.putExtra("title1",String.valueOf(transaction.getTitle()));
                intent.putExtra("amount",String.valueOf(transaction.getAmount()));
                intent.putExtra("date",String.valueOf(transaction.getDate()));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return translist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Amount;
        public TextView Title;
        public TextView Date;
        public RelativeLayout bottom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Amount=itemView.findViewById(R.id.amount);
            Title=itemView.findViewById(R.id.tit1);
            Date=itemView.findViewById(R.id.date);
            bottom=itemView.findViewById(R.id.trans2);

        }
    }
}
