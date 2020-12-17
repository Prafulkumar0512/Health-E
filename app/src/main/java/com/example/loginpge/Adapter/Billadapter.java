package com.example.loginpge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginpge.R;

import java.util.ArrayList;

public class Billadapter extends ArrayAdapter<String> {
    public Billadapter(@NonNull Context context, ArrayList<String> billlist) {
        super(context,0,billlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initview(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initview(position, convertView, parent);
    }

    private View initview(int position,View convertview,ViewGroup parent){
        if(convertview==null){
            convertview= LayoutInflater.from(getContext()).inflate(R.layout.spinner_text,parent,false);
        }
        TextView text=convertview.findViewById(R.id.billname);
        String bill= getItem(position);
        if(convertview!=null){
            text.setText(bill);
        }
        return convertview;
    }
}
