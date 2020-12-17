package com.example.loginpge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.loginpge.Adapter.Paidtable;
import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Tablelist;

import java.util.ArrayList;
import java.util.List;

public class billhistory extends AppCompatActivity {
    private RecyclerView tablelist;
    private DatabaseHandler db=new DatabaseHandler(billhistory.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billhistory);
        tablelist=findViewById(R.id.tablelist);
        ArrayList<Tablelist>tablelists=new ArrayList<>();
        List<Tablelist>tablelists1=db.gettable();
        for(Tablelist t:tablelists1){
            tablelists.add(t);
        }
        Paidtable ptable=new Paidtable(billhistory.this,billhistory.this,tablelists);
        tablelist.setAdapter(ptable);
        tablelist.setHasFixedSize(true);
        tablelist.setLayoutManager(new LinearLayoutManager(billhistory.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menut,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                alertbox();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    void alertbox(){
        AlertDialog.Builder builder=new AlertDialog.Builder(billhistory.this);
        if(db.Counttable()==0) {
            AlertDialog.Builder builder1=new AlertDialog.Builder(billhistory.this);
            builder1.setTitle("Empty Table");
            builder1.setMessage("The Table is Empty");
            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder1.create().show();
        }
        else {
            builder.setTitle("Delete All ?");
            builder.setMessage("Are you sure you want to delete all Bill Payment History ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deletetable();
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }

    }
}