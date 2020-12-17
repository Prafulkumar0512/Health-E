package com.example.loginpge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Profile extends AppCompatActivity {
    private EditText Name,Lastname;
    private EditText Email;
    private EditText Dob;
    private EditText nation;
    private EditText anydisease1;
    private EditText anydisease2;
    private EditText anydisease3;
    private EditText pass;
    private Button update,setdate;
    private ArrayList<Data>Infolist=new ArrayList<>();
    private DatabaseHandler db=new DatabaseHandler(Profile.this);
    private int age;
    private DatePickerDialog datepicker;
    private TextView fullname;
    private int year1,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Name=findViewById(R.id.name1);
        Email=findViewById(R.id.email1);
        Dob=findViewById(R.id.age);
        nation=findViewById(R.id.nation1);
        anydisease1=findViewById(R.id.disease11);
        anydisease2=findViewById(R.id.disease21);
        anydisease3=findViewById(R.id.disease31);
        pass=findViewById(R.id.epass1);
        update=findViewById(R.id.update);
        setdate=findViewById(R.id.update1);
        Lastname=findViewById(R.id.lname1);
        fullname=findViewById(R.id.fullname);


        List<Data> infolist=db.getinfo();
        for(Data data:infolist){
            Infolist.add(data);
        }
        fullname.setText("Name: " + Infolist.get(0).getFirstname()+ " " + Infolist.get(0).getLastname());
        Name.setText(Infolist.get(0).getFirstname());
        Lastname.setText(Infolist.get(0).getLastname());
        Email.setText(Infolist.get(0).getEmail());
        Dob.setText(Infolist.get(0).getDate());
        nation.setText(Infolist.get(0).getCountry());
        anydisease1.setText(Infolist.get(0).getAnydisease1());
        anydisease2.setText(Infolist.get(0).getAnydisease2());
        anydisease3.setText(Infolist.get(0).getAnydisease3());
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int day=cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);
                datepicker=new DatePickerDialog(
                        Profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        year1=year;
                        String date=day + "/" + month + "/" + year;
                        Dob.setText(date);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname=Name.getText().toString();
                String lname=Lastname.getText().toString();
                String email=Email.getText().toString();
                String dob=Dob.getText().toString();
                String Nation=nation.getText().toString();
                String Anydisease1=anydisease1.getText().toString();
                String Anydisease2=anydisease2.getText().toString();
                String Anydisease3=anydisease3.getText().toString();
                String Pass=pass.getText().toString();
                id=Infolist.get(0).getId();
                int currentyear=Calendar.getInstance().get(Calendar.YEAR);
                age=currentyear-year1;

                if(db.checkpass(Pass)){
                    Data data=new Data(id,fname,lname,email,dob,age,Nation,Anydisease1,Anydisease2,Anydisease3);
                    long l=db.updateinfo(data);
                    if(l==1){
                        Toast.makeText(Profile.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Profile.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                    Intent i = new Intent(Profile.this, Profile.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(i);
                    overridePendingTransition(0, 0);

                }
                else
                {
                    Toast.makeText(Profile.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}