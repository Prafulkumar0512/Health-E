package com.example.loginpge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Transaction;

import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class Updateexpensepg extends AppCompatActivity {
    private EditText title;
    private EditText amout;
    private TextView Date;
    private Button setdate;
    private Button addtrans,delete;
    private DatePickerDialog datepicker1;
    private DatabaseHandler db1=new DatabaseHandler(Updateexpensepg.this);
    private String title1,amount,date,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);
        title=findViewById(R.id.edtx12);
        amout=findViewById(R.id.edtx22);
        Date=findViewById(R.id.date12);
        setdate=findViewById(R.id.setd12);
        addtrans=findViewById(R.id.adtrans2);
        delete=findViewById(R.id.delete);
        getintentdata();
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                datepicker1 = new DatePickerDialog(
                        Updateexpensepg.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        Date.setText(date);
                    }
                }, year, month, day);
                datepicker1.show();
            }
        });
        addtrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tit=title.getText().toString();
                String Amont=amout.getText().toString();
                String dat=Date.getText().toString();
                Transaction trans1=new Transaction(Integer.parseInt(id),tit,Amont,dat);
                long l=db1.updatedata(trans1);
                if (l==-1){

                    Toast.makeText(Updateexpensepg.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Updateexpensepg.this, R.string.update,Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(Updateexpensepg.this, Updateexpensepg.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actionbar();
            }
        });

    }
    void getintentdata(){
        if( getIntent().hasExtra("id")&& getIntent().hasExtra("title1") && getIntent().hasExtra("amount") && getIntent().hasExtra("date"))
        {
            id=getIntent().getStringExtra("id");
            title1=getIntent().getStringExtra("title1");
            amount=getIntent().getStringExtra("amount");
            date=getIntent().getStringExtra("date");

            title.setText(title1);
            amout.setText(amount);
            Date.setText(date);

        }
        else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void Actionbar(){
        AlertDialog.Builder builder=new AlertDialog.Builder(Updateexpensepg.this);
        builder.setTitle("Delete " + title1 +" ?");
        builder.setMessage("Are you sure  you want to delete " + title1 + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tit=title.getText().toString();
                String Amont=amout.getText().toString();
                String dat=Date.getText().toString();
                Transaction trans1=new Transaction(Integer.parseInt(id),tit,Amont,dat);
                db1.Deletedata(trans1);
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